package com.gomo.app.support.auth.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.support.auth.application.port.SendAuthCodePortOut;
import com.gomo.app.support.auth.domain.repository.AuthCodeRepository;

@DisplayName("[Application Unit]: 인증 코드 생성 테스트")
@ExtendWith(MockitoExtension.class)
class CreateAuthCodeUseCaseTest {

	@InjectMocks
	private CreateAuthCodeUseCase sut;

	@Mock
	private SendAuthCodePortOut sendAuthCodePortOut;

	@Mock
	private AuthCodeRepository authCodeRepository;

	@DisplayName("인증 코드를 생성한다.")
	@Test
	void create_auth_code() {
		String actual = sut.sendToEmail("email@gmail.com");

		assertThat(actual).isNotBlank();
		verify(authCodeRepository, times(1)).save(any(), any());
		verify(sendAuthCodePortOut, times(1)).toEmail(any(), any());
	}
}
