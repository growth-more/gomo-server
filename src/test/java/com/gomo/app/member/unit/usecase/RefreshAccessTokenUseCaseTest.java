package com.gomo.app.member.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.common.util.JwtUtil;
import com.gomo.app.member.application.RefreshAccessTokenUseCase;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.exception.MemberAuthenticationFailedException;
import com.gomo.app.member.exception.code.MemberErrorCode;
import com.gomo.app.member.infrastructure.JwtSessionRedisService;
import com.gomo.app.member.presentation.response.LoginMemberResponse;

@DisplayName("[Application Unit]: AccessToken 재발급 테스트")
@ExtendWith(MockitoExtension.class)
public class RefreshAccessTokenUseCaseTest {
	@InjectMocks
	RefreshAccessTokenUseCase sut;

	@Mock
	JwtUtil jwtUtil;

	@Mock
	JwtSessionRedisService jwtSessionRedisService;

	private static final String REFRESH_TOKEN = "refresh-token";
	private static final String NEW_REFRESH_TOKEN = "new-refresh-token";
	private static final String NEW_ACCESS_TOKEN = "new-access-token";

	private static final long EXPIRES_IN = 3600;

	@DisplayName("토큰 재발행에 성공한다")
	@Test
	void refresh_token_successfully() {
		Member member = MemberFixture.member();

		doReturn(member.getId().toString()).when(jwtUtil).extractMemberId(REFRESH_TOKEN);
		doReturn(REFRESH_TOKEN).when(jwtSessionRedisService).getRefreshToken(member.uuid());

		doReturn(NEW_REFRESH_TOKEN).when(jwtUtil).generateRefreshToken(member.uuid());
		doReturn(NEW_ACCESS_TOKEN).when(jwtUtil).generateAccessToken(member.uuid());
		doReturn(EXPIRES_IN).when(jwtUtil).extractExpirationTime(anyString());

		doNothing().when(jwtSessionRedisService).updateRefreshToken(member.uuid(), NEW_REFRESH_TOKEN);

		LoginMemberResponse expected = LoginMemberResponse.of(member.uuid(), NEW_ACCESS_TOKEN, NEW_REFRESH_TOKEN, EXPIRES_IN);
		LoginMemberResponse actual = sut.refresh(REFRESH_TOKEN);

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}

	@DisplayName("저장된 토큰값과 다를 경우, 갱신에 실패한다.")
	@Test
	void refresh_token_fail_with_wrong_token() {
		Member member = MemberFixture.member();

		doReturn(member.getId().toString()).when(jwtUtil).extractMemberId(REFRESH_TOKEN);
		doReturn("WORNG_TOKEN").when(jwtSessionRedisService).getRefreshToken(member.uuid());

		assertThatThrownBy(() -> sut.refresh(REFRESH_TOKEN))
			.isInstanceOf(MemberAuthenticationFailedException.class)
			.hasMessageContaining(MemberErrorCode.AUTHENTICATION_FAILED.getMessage());
	}

	@DisplayName("전달된 REFRESH 토큰값이 null일 경우 재발행에 실패한다")
	@Test
	void refresh_token_fail_with_null_token() {
		assertThatThrownBy(() -> sut.refresh(null))
			.isInstanceOf(MemberAuthenticationFailedException.class)
			.hasMessageContaining(MemberErrorCode.AUTHENTICATION_FAILED.getMessage());
	}
}
