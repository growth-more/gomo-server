package com.gomo.app.core.member.application.usecase;

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
import com.gomo.app.common.security.jwt.application.port.VerifyJwtPortIn;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.core.member.fixture.MemberFixture;

@DisplayName("[Application unit] : 비밀번호 초기화 테스트")
@ExtendWith(MockitoExtension.class)
class ResetPasswordUseCaseTest {

	@InjectMocks
	private ResetPasswordUseCase sut;

	@Mock
	private VerifyJwtPortIn verifyJwtPortIn;

	@Mock
	private EncodePasswordPortIn encodePasswordPortIn;

	@Mock
	private MemberService memberService;

	@DisplayName("비밀번호를 초기화한다.")
	@Test
	void reset_password() {
		Member member = MemberFixture.create();
		String encoded = "encoded_password";
		doReturn(true).when(verifyJwtPortIn).validateToken(any());
		doReturn(member).when(memberService).findByEmail(any());
		doReturn(encoded).when(encodePasswordPortIn).encode(any());

		sut.reset(member.email(), "New1234@", "temporaryToken");

		assertThat(member.password()).isEqualTo(encoded);
	}

	@DisplayName("임시 코드 검증에 실패한다.")
	@Test
	void fail_validate_temporary_code() {
		Member member = MemberFixture.create();
		doReturn(false).when(verifyJwtPortIn).validateToken(null);

		assertThatThrownBy(() -> sut.reset(member.email(), "New1234@", null)).isInstanceOf(IllegalArgumentException.class);
	}
}
