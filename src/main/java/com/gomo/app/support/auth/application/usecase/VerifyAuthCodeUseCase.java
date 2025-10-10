package com.gomo.app.support.auth.application.usecase;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.support.auth.application.port.VerifyAuthCodePortIn;
import com.gomo.app.support.auth.domain.repository.AuthCodeRepository;
import com.gomo.app.support.auth.exception.AuthErrorCode;
import com.gomo.app.support.auth.exception.InvalidAuthCodeException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class VerifyAuthCodeUseCase implements VerifyAuthCodePortIn {

	private final AuthCodeRepository authCodeRepository;

	@Override
	public void verify(String email, String authCode) {
		authCodeRepository.findByEmail(email)
			.filter(code -> code.equals(authCode))
			.orElseThrow(() -> new InvalidAuthCodeException(AuthErrorCode.INVALID_AUTH_CODE));
		authCodeRepository.delete(email);
	}
}
