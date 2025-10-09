package com.gomo.app.core.member.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.core.member.fixture.MemberFixture;

@DisplayName("[Application unit]: OAuth 회원 로그인 테스트")
@ExtendWith(MockitoExtension.class)
class OAuthLoginMemberUseCaseTest {

	@InjectMocks
	private OAuthLoginMemberUseCase sut;

	@Mock
	private MemberRepository memberRepository;

	@DisplayName("회원을 조회하고, 로그인 날짜를 갱신한다.")
	@Test
	void oauth_login_member() {
		Member member = MemberFixture.create();
		doReturn(Optional.of(member)).when(memberRepository).findByEmail(any());
		Optional<UUID> actual = sut.oauthAuthenticate(member.email());
		assertThat(actual.isPresent()).isTrue();
	}

	@DisplayName("OAuth 회원이 존재하지 않는다.")
	@Test
	void oauth_login_member_not_found() {
		doReturn(Optional.empty()).when(memberRepository).findByEmail(any());
		Optional<UUID> actual = sut.oauthAuthenticate("email@gmail.com");
		assertThat(actual.isPresent()).isFalse();
	}
}
