package com.gomo.app.common.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class Events {

	private static ApplicationEventPublisher applicationEventPublisher;

	static void initialize(ApplicationEventPublisher applicationEventPublisher) {
		Events.applicationEventPublisher = applicationEventPublisher;
	}

	public static void raise(Object event) {

	}
}
