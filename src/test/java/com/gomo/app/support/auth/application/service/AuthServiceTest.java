package com.gomo.app.support.auth.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.domain.model.ActivateStatus;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.fixture.MemberFixture;
import com.gomo.app.support.auth.application.port.dto.AuthTokenDto;
import com.gomo.app.support.auth.application.port.out.JwtVerifier;
import com.gomo.app.support.auth.application.port.out.PrincipalLoginProcessor;
import com.gomo.app.support.auth.domain.model.AuthToken;

@DisplayName("[Application Unit]: 사용자 로그인 테스트")
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

	@InjectMocks
	private AuthService sut;

	@Mock
	private PrincipalLoginProcessor principalLoginProcessor;

	@Mock
	private JwtVerifier jwtVerifier;

	@Mock
	private AuthTokenService authTokenService;

	@DisplayName("사용자가 로그인에 성공한다.")
	@Test
	void login_success() {
		Member member = MemberFixture.create(ActivateStatus.ACTIVE);
		AuthToken authToken = AuthToken.of("access", "refresh");
		AuthTokenDto expected = AuthTokenDto.of(member.getId(), authToken.getAccessToken(), authToken.getRefreshToken(), 1L);
		doReturn(member.getId()).when(principalLoginProcessor).login(anyString(), anyString());
		doReturn(authToken).when(authTokenService).create(any());
		doReturn(1L).when(jwtVerifier).extractExpirationTime(anyString());

		AuthTokenDto actual = sut.login(member.email(), member.password());

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}
}
