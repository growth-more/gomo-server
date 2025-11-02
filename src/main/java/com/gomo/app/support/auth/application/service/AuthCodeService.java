package com.gomo.app.support.auth.application.service;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.support.auth.application.port.in.AuthCodeIssuer;
import com.gomo.app.support.auth.application.port.in.AuthCodeVerifier;
import com.gomo.app.support.auth.application.port.out.AuthCodeSender;
import com.gomo.app.support.auth.domain.exception.AuthErrorCode;
import com.gomo.app.support.auth.domain.exception.InvalidAuthCodeException;
import com.gomo.app.support.auth.domain.model.AuthCode;
import com.gomo.app.support.auth.domain.repository.AuthCodeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class AuthCodeService implements AuthCodeIssuer, AuthCodeVerifier {

	private final AuthCodeRepository authCodeRepository;
	private final AuthCodeSender authCodeSender;

	// TODO [2025-10-10] jhl221123 : 회원 도메인에 이메일 검증(Email.of())을 요청해야 합니다.
	// TODO [2025-10-10] jhl221123 : 빈번한 요청에 대비해야 합니다.
	@Override
	@AuditLog(action = "AUTH_CODE_SEND")
	public void sendToEmail(String email) {
		AuthCode authCode = AuthCode.generate();
		authCodeRepository.save(email, authCode.getValue());
		authCodeSender.send(email, authCode.getValue());
	}

	@Override
	@AuditLog(action = "AUTH_CODE_VERIFY_AND_DELETE")
	public void verify(String email, String authCode) {
		authCodeRepository.findByEmail(email)
			.filter(code -> code.equals(authCode))
			.orElseThrow(() -> new InvalidAuthCodeException(AuthErrorCode.INVALID_AUTH_CODE));
		authCodeRepository.delete(email);
	}
}
