package com.gomo.app.support.event.infrastructure.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.support.event.application.port.ProcessEventEntryPortIn;
import com.gomo.app.support.event.domain.model.EventEntry;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class IdempotentEventEntryConsumerAspect {

	private final ProcessEventEntryPortIn processEventEntryPortIn;

	/**
	 * Intercepts and manages the execution of any method annotated with @IdempotentEventEntryConsumer.
	 * This aspect enriches the annotated method with the following critical functionalities:
	 * <ul>
	 *   <li><b>Idempotency:</b> Checks if the event has already been processed to prevent duplicate execution.</li>
	 *   <li><b>Atomicity:</b> Wraps the entire operation (recording the event and executing the business logic) in a single transaction.</li>
	 *   <li><b>Centralized Logging:</b> Automatically logs the outcome (success, failure, or skip) of the event processing.</li>
	 * </ul>
	 *
	 * @param joinPoint Represents the intercepted method execution.
	 * @return The result of the original method execution, or null if skipped.
	 * @throws Throwable if the intercepted method throws an exception, triggering a transaction rollback.
	 */
	@Around("@annotation(com.gomo.app.support.event.application.port.IdempotentEventEntryConsumer)")
	@Transactional(rollbackFor = Exception.class)
	public Object handleEvent(ProceedingJoinPoint joinPoint) throws Throwable {
		EventEntry eventEntry = findEventEntry(joinPoint);
		if (eventEntry == null) {
			log.warn("Target method [{}] has no EventEntry parameter. Skipping idempotency check.", joinPoint.getSignature().toShortString());
			return joinPoint.proceed();
		}

		String eventEntryId = String.valueOf(eventEntry.getId());
		String consumerName = joinPoint.getTarget().getClass().getName();
		try {
			if (processEventEntryPortIn.isAlreadyProcessed(eventEntryId, consumerName)) {
				log.debug("[{}] Event id={} has already been processed. Skipping.", consumerName, eventEntryId);
				return null;
			}
			processEventEntryPortIn.process(eventEntryId, consumerName);

			Object result = joinPoint.proceed();
			log.info("[{}] Successfully processed event id={}", consumerName, eventEntryId);
			return result;
		} catch (Exception e) {
			log.error("[{}] Failed to process event id={}, errorType={}, errorMessage={}", consumerName, eventEntryId, e.getClass().getSimpleName(), e.getMessage(), e);
			throw e;
		}
	}

	private EventEntry findEventEntry(ProceedingJoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		if (args == null) {
			return null;
		}
		for (Object arg : args) {
			if (arg instanceof EventEntry) {
				return (EventEntry)arg;
			}
		}
		return null;
	}
}
