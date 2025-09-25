package com.gomo.app.support.auth.application;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.support.auth.application.port.CreateAuthCodePortIn;
import com.gomo.app.support.auth.application.port.SendAuthCodePortOut;
import com.gomo.app.support.auth.domain.model.AuthCode;
import com.gomo.app.support.auth.domain.repository.AuthCodeRepository;
import com.gomo.app.support.logging.AuditLog;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
public class CreateAuthCodeUseCase implements CreateAuthCodePortIn {

	private final SendAuthCodePortOut sendAuthCodePortOut;
	private final AuthCodeRepository authCodeRepository;

	@AuditLog(action = "SEND_AUTH_CODE")
	@Override
	public void sendToEmail(String email) {
		AuthCode authCode = AuthCode.generate();
		authCodeRepository.save(email, authCode.getValue());
		sendAuthCodePortOut.toEmail(email, authCode.getValue());
	}
}
