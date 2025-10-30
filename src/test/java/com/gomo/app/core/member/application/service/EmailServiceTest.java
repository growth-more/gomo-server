package com.gomo.app.core.member.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.domain.exception.EmailDuplicatedException;
import com.gomo.app.core.member.domain.exception.code.EmailErrorCode;
import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.repository.MemberRepository;

@DisplayName("[Application unit] : 사용자 이메일 중복 검증 테스트")
@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

	@InjectMocks
	private EmailService sut;

	@Mock
	private MemberRepository memberRepository;

	@DisplayName("이메일이 중복되지 않는다.")
	@Test
	void check_non_duplicated_email() {
		doReturn(Optional.empty()).when(memberRepository).findByEmail(any(Email.class));

		assertThatCode(() -> sut.validateDuplicated("nonexistent@naver.com")).doesNotThrowAnyException();
	}

	@DisplayName("이메일이 중복된다.")
	@Test
	void check_duplicated_email() {
		doReturn(Optional.of(mock(Member.class))).when(memberRepository).findByEmail(any(Email.class));

		assertThatThrownBy(() -> sut.validateDuplicated("gomo@naver.com"))
			.isInstanceOf(EmailDuplicatedException.class)
			.hasMessageContaining(EmailErrorCode.DUPLICATED.getMessage());
	}
}
