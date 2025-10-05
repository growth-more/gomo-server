package com.gomo.app.support.event.infrastructure;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Component;

import com.gomo.app.common.event.Event;
import com.gomo.app.common.event.EventRouting;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EventRouter {

	private static final String BASE_PACKAGE = "com.gomo";
	
	private final Map<String, Class<? extends Event>> eventMapByName;

	public EventRouter() {
		this.eventMapByName = scanDomainEvents(BASE_PACKAGE);
	}

	/**
	 * Scans the given base package for all classes that implement the {@link Event} class.
	 * Use false to search bean haven't @Component
	 *
	 * @param basePackage the root package to scan
	 * @return a map where the key is the event's simple name and the value is the event's Class object
	 */
	private Map<String, Class<? extends Event>> scanDomainEvents(String basePackage) {
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AssignableTypeFilter(Event.class));
		return scanner.findCandidateComponents(basePackage).stream()
			.map(this::loadClass)
			.filter(Objects::nonNull)
			.collect(Collectors.toMap(Class::getSimpleName, Function.identity()));
	}

	@SuppressWarnings("unchecked")
	private Class<? extends Event> loadClass(BeanDefinition beanDefinition) {
		try {
			return (Class<? extends Event>)Class.forName(beanDefinition.getBeanClassName());
		} catch (ClassNotFoundException e) {
			log.error("Cannot load class {}", beanDefinition.getBeanClassName(), e);
			return null;
		}
	}

	/**
	 * Retrieves the target exchange name for a given event.
	 *
	 * @param eventName the simple name of the event class (e.g., "QuestCompletedEvent")
	 * @return the exchange name defined in the event's {@link EventRouting} annotation
	 * @throws IllegalStateException if the event type is not found or is missing routing information
	 */
	public String getExchange(String eventName) {
		EventRouting annotation = getRoutingInfo(eventName);
		return annotation.exchange();
	}

	/**
	 * Retrieves the target routing key for a given event.
	 *
	 * @param eventName the simple name of the event class (e.g., "QuestCompletedEvent")
	 * @return the routing key defined in the event's {@link EventRouting} annotation
	 * @throws IllegalStateException if the event type is not found or is missing routing information
	 */
	public String getRoutingKey(String eventName) {
		EventRouting annotation = getRoutingInfo(eventName);
		return annotation.routingKey();
	}

	@NotNull
	private EventRouting getRoutingInfo(String eventName) {
		Class<?> eventClass = eventMapByName.get(eventName);
		if (eventClass == null) {
			throw new IllegalStateException("Event " + eventName + " not found");
		}

		EventRouting annotation = eventClass.getAnnotation(EventRouting.class);
		if (annotation == null) {
			throw new IllegalStateException("Event " + eventName + " haven't routing annotation");
		}
		return annotation;
	}
}
