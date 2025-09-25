package com.gomo.app.auth.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.common.jwt.port.VerifyJwtPortIn;
import com.gomo.app.core.member.application.adapter.PasswordAdapter;
import com.gomo.app.core.member.common.fixture.MemberFixture;
import com.gomo.app.core.member.domain.model.ActivateStatus;
import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.support.auth.application.AuthPrincipalUseCase;
import com.gomo.app.support.auth.application.CreateAuthTokenUseCase;
import com.gomo.app.support.auth.domain.model.AuthToken;
import com.gomo.app.support.auth.presentation.response.AuthTokenResponse;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Application Unit]: 사용자 로그인 테스트")
public class AuthPrincipalUseCaseTest {

	@InjectMocks
	AuthPrincipalUseCase sut;

	@Mock
	private MemberService memberService;

	@Mock
	private PasswordAdapter passwordAdapter;

	@Mock
	private CreateAuthTokenUseCase createAuthTokenUseCase;

	@Mock
	private VerifyJwtPortIn verifyJwtPortIn;

	@DisplayName("사용자가 로그인에 성공한다.")
	@Test
	void authenticate_success() {
		Member member = MemberFixture.member(ActivateStatus.ACTIVE);
		AuthToken authToken = AuthToken.of("access", "refresh");
		AuthTokenResponse expected = AuthTokenResponse.of(member.uuid(), authToken, 1L);

		doReturn(member).when(memberService).findByEmail(any(Email.class));

		doReturn(true).when(passwordAdapter).matches(anyString(), anyString());
		doReturn(authToken).when(createAuthTokenUseCase).create(any());
		doReturn(1L).when(verifyJwtPortIn).extractExpirationTime(anyString());

		AuthTokenResponse actual = sut.authenticate(member.getEmail().getEmail(), member.getPassword().getPassword());

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}
}
