package com.gomo.app.support.auth.application.usecase;

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
class CreateAuthCodeUseCase implements CreateAuthCodePortIn {

	private final SendAuthCodePortOut sendAuthCodePortOut;
	private final AuthCodeRepository authCodeRepository;

	// TODO [2025-10-10] jhl221123 : 회원 도메인에 이메일 검증(Email.of())을 요청해야 합니다.
	// TODO [2025-10-10] jhl221123 : 빈번한 요청에 대비해야 합니다.
	@AuditLog(action = "SEND_AUTH_CODE")
	@Override
	public String sendToEmail(String email) {
		AuthCode authCode = AuthCode.generate();
		authCodeRepository.save(email, authCode.getValue());
		sendAuthCodePortOut.toEmail(email, authCode.getValue());
		return authCode.getValue();
	}
}
