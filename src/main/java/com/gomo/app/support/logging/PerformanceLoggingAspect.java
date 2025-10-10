package com.gomo.app.support.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@Order(1)
public class PerformanceLoggingAspect {

	@Around("@annotation(com.gomo.app.support.logging.Timed)")
	public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		String className = signature.getDeclaringType().getSimpleName();
		String methodName = signature.getName();

		StopWatch stopWatch = new StopWatch();
		try {
			stopWatch.start();
			return joinPoint.proceed();
		} finally {
			if (stopWatch.isRunning()) {
				stopWatch.stop();
			}
			log.debug("class={}, method={}, executionTime={}ms", className, methodName, stopWatch.getTotalTimeMillis());
		}
	}
}
