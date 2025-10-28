package com.gomo.app.support.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.gomo.app.common.logging.AuditLog;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@Order(2)
public class AuditLoggingAspect {

	@Around("@annotation(com.gomo.app.common.logging.AuditLog)")
	public Object logAuditAction(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		AuditLog auditLog = signature.getMethod().getAnnotation(AuditLog.class);
		String action = !auditLog.action().isBlank() ?
			auditLog.action() : signature.getDeclaringType().getSimpleName() + "." + signature.getName();

		try {
			Object result = joinPoint.proceed();
			log.info("action={}, status=success, result={}", action, result);
			return result;
		} catch (Throwable throwable) {
			log.error("action={}, status=failed, exception={}", action, throwable.getMessage(), throwable);
			throw throwable;
		}
	}
}
