package com.gomo.app.core.auth.application.service;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.auth.application.port.in.AuthCodeIssuer;
import com.gomo.app.core.auth.application.port.out.AuthCodeSender;
import com.gomo.app.core.auth.application.port.out.PrincipalEmailChecker;
import com.gomo.app.core.auth.domain.exception.AuthCodeCreateFailException;
import com.gomo.app.core.auth.domain.exception.AuthErrorCode;
import com.gomo.app.core.auth.domain.exception.InvalidAuthCodeException;
import com.gomo.app.core.auth.domain.model.AuthCode;
import com.gomo.app.core.auth.domain.repository.AuthCodeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class AuthCodeService implements AuthCodeIssuer {

	private final PrincipalEmailChecker principalEmailChecker;
	private final AuthCodeSender authCodeSender;
	private final AuthCodeRepository authCodeRepository;

	@Override
	@AuditLog(action = "AUTH_CODE_ISSUE_FOR_SIGN_UP")
	public void issueForSignUp(String email) {
		if (principalEmailChecker.exists(email)) {
			throw new AuthCodeCreateFailException(AuthErrorCode.PRINCIPAL_DUPLICATED);
		}
		createAndSendAuthCode(email);
	}

	@Override
	@AuditLog(action = "AUTH_CODE_ISSUE_FOR_PW_RESET")
	public void issueForPasswordReset(String email) {
		if (!principalEmailChecker.exists(email)) {
			throw new AuthCodeCreateFailException(AuthErrorCode.PRINCIPAL_NOT_FOUND);
		}
		createAndSendAuthCode(email);
	}

	private void createAndSendAuthCode(String email) {
		AuthCode authCode = AuthCode.generate();
		authCodeRepository.save(email, authCode.getValue());
		authCodeSender.send(email, authCode.getValue());
	}

	void verify(String email, String authCode) {
		authCodeRepository.findByEmail(email)
			.filter(code -> code.equals(authCode))
			.orElseThrow(() -> new InvalidAuthCodeException(AuthErrorCode.INVALID_AUTH_CODE));
		authCodeRepository.delete(email);
	}
}
