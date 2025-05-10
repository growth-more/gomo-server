package com.gomo.app.auth.application;

import java.time.LocalDateTime;

import com.gomo.app.auth.domain.model.AuthToken;
import com.gomo.app.auth.infrastructure.oauth.OAuthProvider;
import com.gomo.app.auth.infrastructure.oauth.OAuthProviderFactory;
import com.gomo.app.auth.presentation.response.AuthTokenResponse;
import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.OAuthUserInfo;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.domain.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class OAuthUseCase {

	private final OAuthProviderFactory providerFactory;
	private final MemberService memberService;
	private final MemberRepository memberRepository;
	private final AuthTokenGenerator authTokenGenerator;

	public AuthTokenResponse login(String providerName, String code) {
		OAuthProvider provider = providerFactory.getProvider(providerName);
		OAuthUserInfo userInfo = provider.authenticate(code);

		Member member = memberRepository.findByEmail(Email.of(userInfo.getEmail()))
			.orElseGet(() -> memberService.oauthCreateMember(userInfo, providerName));
		memberService.checkActivated(member);
		member.updateLastLoginDateTime(LocalDateTime.now());

		AuthToken authToken = authTokenGenerator.generate(member.uuid());
		long refreshExpirationTime = authTokenGenerator.getRefreshTokenExpirationTime(authToken.getRefreshToken());

		return AuthTokenResponse.of(member.uuid(), authToken, refreshExpirationTime);
	}
}
