package com.gomo.app.core.member.infrastructure.adapter;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Domain Unit]: PasswordService 테스트")
public class PasswordAdapterTest {

	private static final String RAW_PASSWORD = "password";
	private static final String ENCODED_PASSWORD = "encoded_password";

	@InjectMocks
	PasswordAdapter sut;

	@Mock
	PasswordEncoder passwordEncoder;

	@DisplayName("비밀번호를 인코딩한다")
	@Test
	void encodePassword() {
		when(passwordEncoder.encode(RAW_PASSWORD)).thenReturn(ENCODED_PASSWORD);

		String actual = sut.encode(RAW_PASSWORD);
		assertThat(actual).isEqualTo(ENCODED_PASSWORD);
	}

	@DisplayName("입력한 비밀번호와 기존 비밀번호가 동일한 경우, true를 반환한다.")
	@Test
	void return_true_with_matched_password() {
		when(passwordEncoder.matches(RAW_PASSWORD, ENCODED_PASSWORD)).thenReturn(true);

		boolean actual = sut.matches(RAW_PASSWORD, ENCODED_PASSWORD);
		assertThat(actual).isTrue();
	}

	@DisplayName("입력한 비밀번호와 기존 비밀번호가 동일한 경우, true를 반환한다.")
	@Test
	void return_true_with_unmatched_password() {
		when(passwordEncoder.matches(RAW_PASSWORD, ENCODED_PASSWORD)).thenReturn(false);

		boolean actual = sut.matches(RAW_PASSWORD, ENCODED_PASSWORD);
		assertThat(actual).isFalse();
	}
}
