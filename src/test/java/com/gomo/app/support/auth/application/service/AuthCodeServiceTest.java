package com.gomo.app.support.auth.application.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.support.auth.application.port.out.AuthCodeSender;
import com.gomo.app.support.auth.domain.exception.InvalidAuthCodeException;
import com.gomo.app.support.auth.domain.repository.AuthCodeRepository;

@DisplayName("[Application Unit]: 인증 코드 생성 테스트")
@ExtendWith(MockitoExtension.class)
class AuthCodeServiceTest {

	@InjectMocks
	private AuthCodeService sut;

	@Mock
	private AuthCodeSender authCodeSender;

	@Mock
	private AuthCodeRepository authCodeRepository;

	@DisplayName("인증 코드 생성 후, 전송한다.")
	@Test
	void issue_auth_code() {
		sut.sendToEmail("email@gmail.com");

		verify(authCodeRepository, times(1)).save(any(), any());
		verify(authCodeSender, times(1)).send(any(), any());
	}

	@DisplayName("인증 코드 검증 후, 삭제한다.")
	@Test
	void verify_auth_code() {
		String authCode = "123456";
		doReturn(Optional.of(authCode)).when(authCodeRepository).findByEmail(any());

		sut.verify("email@gmail.com", authCode);

		verify(authCodeRepository, times(1)).delete(any());
	}

	@DisplayName("일치하지 않는 인증 코드로 검증한다.")
	@Test
	void verify_auth_code_with_invalid_code() {
		String authCode = "123456";
		String invalidAuthCode = "654321";
		doReturn(Optional.of(authCode)).when(authCodeRepository).findByEmail(any());

		Assertions.assertThatThrownBy(() -> sut.verify("email@gmail.com", invalidAuthCode)).isExactlyInstanceOf(InvalidAuthCodeException.class);
	}
}
