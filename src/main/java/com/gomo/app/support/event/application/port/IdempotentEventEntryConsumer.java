package com.gomo.app.support.event.application.port;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a policy that an event consumer method must be idempotent.
 * <p>
 * This annotation serves as a contract for the application layer. Any method marked
 * with {@code @IdempotentEventEntryConsumer} is guaranteed by the framework to be
 * enriched with the following cross-cutting concerns:
 * <ul>
 *   <li><b>Idempotency:</b> Ensures that the same event is not processed more than once.</li>
 *   <li><b>Atomicity:</b> Executes the event processing logic and the idempotency check within a single, atomic transaction.</li>
 *   <li><b>Centralized Logging:</b> Provides consistent logging for the lifecycle of the event processing (e.g., success, failure, skip).</li>
 * </ul>
 * The technical implementation of this policy is handled by an Aspect in the
 * infrastructure layer, keeping the application logic clean and focused.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IdempotentEventEntryConsumer {
}
