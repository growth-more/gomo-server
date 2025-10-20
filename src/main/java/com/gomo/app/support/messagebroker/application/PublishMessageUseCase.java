package com.gomo.app.support.messagebroker.application;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.support.logging.AuditLog;
import com.gomo.app.support.messagebroker.application.port.MessageBrokerClientPortOut;
import com.gomo.app.support.messagebroker.application.port.PublishMessagePortIn;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class PublishMessageUseCase implements PublishMessagePortIn {

	private final MessageBrokerClientPortOut messageBrokerClientPortOut;

	@AuditLog(action = "PUBLISH_MESSAGE")
	@Override
	public void send(String destination, String key, String message) {
		messageBrokerClientPortOut.send(destination, key, message);
	}
}
