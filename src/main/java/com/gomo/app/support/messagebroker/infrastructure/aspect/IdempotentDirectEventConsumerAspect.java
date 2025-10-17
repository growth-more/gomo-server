package com.gomo.app.support.messagebroker.infrastructure.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.support.messagebroker.domain.model.DirectEvent;
import com.gomo.app.support.messagebroker.domain.repository.ProcessedDirectEventRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Aspect
@Component
public class IdempotentDirectEventConsumerAspect {

	private final ProcessedDirectEventRepository processedDirectEventRepository;

	/**
	 * Intercepts and manages the execution of any method annotated with @IdempotentDirectEventConsumer.
	 * This aspect enriches the annotated method with the following critical functionalities:
	 * <ul>
	 *   <li><b>Idempotency:</b> Checks if the DirectEvent has already been processed to prevent duplicate execution.</li>
	 *   <li><b>Atomicity:</b> Wraps the entire operation (recording the DirectEvent and executing the business logic) in a single transaction.</li>
	 *   <li><b>Centralized Logging:</b> Automatically logs the outcome (success, failure, or skip) of the event processing.</li>
	 * </ul>
	 *
	 * @param joinPoint Represents the intercepted method execution.
	 * @return The result of the original method execution, or null if skipped.
	 * @throws Throwable if the intercepted method throws an exception, triggering a transaction rollback.
	 */
	@Around("@annotation(com.gomo.app.support.messagebroker.application.port.IdempotentDirectEventConsumer)")
	@Transactional(rollbackFor = Exception.class)
	public Object handleEvent(ProceedingJoinPoint joinPoint) throws Throwable {
		DirectEvent directEvent = findDirectEvent(joinPoint);
		if (directEvent == null) {
			log.warn("Target method [{}] has no DirectEvent parameter. Skipping idempotency check.", joinPoint.getSignature().toShortString());
			return joinPoint.proceed();
		}

		String directEventId = String.valueOf(directEvent.getId());
		String consumerName = joinPoint.getTarget().getClass().getSimpleName();
		try {
			if (processedDirectEventRepository.existsByDirectEventIdAndConsumerName(directEventId, consumerName)) {
				log.debug("[{}] DirectEvent id={} has already been processed. Skipping.", consumerName, directEventId);
				return null;
			}
			processedDirectEventRepository.save(directEventId, consumerName);

			Object result = joinPoint.proceed();
			log.info("[{}] Successfully processed DirectEvent id={}", consumerName, directEventId);
			return result;
		} catch (Exception e) {
			log.error("[{}] Failed to process event id={}, errorType={}, errorMessage={}", consumerName, directEventId, e.getClass().getSimpleName(), e.getMessage(), e);
			throw e;
		}
	}

	private DirectEvent findDirectEvent(ProceedingJoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		if (args == null) {
			return null;
		}
		for (Object arg : args) {
			if (arg instanceof DirectEvent) {
				return (DirectEvent)arg;
			}
		}
		return null;
	}
}
