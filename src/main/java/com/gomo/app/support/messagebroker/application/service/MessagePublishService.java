package com.gomo.app.support.messagebroker.application.service;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.support.messagebroker.application.port.in.MessagePublisher;
import com.gomo.app.support.messagebroker.application.port.out.MessageBrokerManager;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class MessagePublishService implements MessagePublisher {

	private final MessageBrokerManager messageBrokerManager;

	@Override
	@AuditLog(action = "MESSAGE_PUBLISH")
	public void send(String destination, String key, String message) {
		messageBrokerManager.send(destination, key, message);
	}
}
