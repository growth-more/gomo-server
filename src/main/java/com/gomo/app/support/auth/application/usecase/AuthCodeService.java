package com.gomo.app.support.auth.application.usecase;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.support.auth.application.port.AuthCodeIssuer;
import com.gomo.app.support.auth.application.port.AuthCodeSender;
import com.gomo.app.support.auth.domain.model.AuthCode;
import com.gomo.app.support.auth.domain.repository.AuthCodeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class AuthCodeService implements AuthCodeIssuer {

	private final AuthCodeRepository authCodeRepository;
	private final AuthCodeSender authCodeSender;

	// TODO [2025-10-10] jhl221123 : 회원 도메인에 이메일 검증(Email.of())을 요청해야 합니다.
	// TODO [2025-10-10] jhl221123 : 빈번한 요청에 대비해야 합니다.
	@AuditLog(action = "SEND_AUTH_CODE")
	@Override
	public String sendToEmail(String email) {
		AuthCode authCode = AuthCode.generate();
		authCodeRepository.save(email, authCode.getValue());
		authCodeSender.send(email, authCode.getValue());
		return authCode.getValue();
	}
}
