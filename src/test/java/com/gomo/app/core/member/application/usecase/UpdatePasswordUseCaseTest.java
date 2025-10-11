package com.gomo.app.core.member.application.usecase;

import static com.gomo.app.core.member.exception.code.MemberErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.common.security.encoder.application.port.EncodePasswordPortIn;
import com.gomo.app.common.security.encoder.application.port.VerifyPasswordPortIn;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.core.member.exception.MemberAuthenticationFailedException;
import com.gomo.app.core.member.fixture.MemberFixture;

@DisplayName("[Application unit] : 비밀번호 변경 테스트")
@ExtendWith(MockitoExtension.class)
class UpdatePasswordUseCaseTest {

	@InjectMocks
	private UpdatePasswordUseCase sut;

	@Mock
	private VerifyPasswordPortIn verifyPasswordPortIn;

	@Mock
	private EncodePasswordPortIn encodePasswordPortIn;

	@Mock
	private MemberService memberService;

	@DisplayName("비밀번호를 변경한다.")
	@Test
	void update_password() {
		Member member = MemberFixture.create();
		String encoded = "encoded_password";
		doReturn(member).when(memberService).find(any());
		doReturn(true).when(verifyPasswordPortIn).matches(any(), any());
		doReturn(encoded).when(encodePasswordPortIn).encode(any());

		sut.update(member.id(), "Origin123@", "New1234@");

		assertThat(member.password()).isEqualTo(encoded);
	}

	@DisplayName("기존 비밀번호 불일치로 비밀번호를 변경하지 못한다.")
	@Test
	void update_password_with_wrong_password() {
		Member member = MemberFixture.create();
		doReturn(member).when(memberService).find(any());
		doReturn(false).when(verifyPasswordPortIn).matches(any(), any());

		assertThatThrownBy(() -> sut.update(member.id(), "Origin123@", "New1234@"))
			.isInstanceOf(MemberAuthenticationFailedException.class)
			.hasMessageContaining(AUTHENTICATION_FAILED.getMessage());
	}
}
