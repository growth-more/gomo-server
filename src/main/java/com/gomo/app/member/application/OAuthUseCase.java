package com.gomo.app.member.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.util.JwtUtil;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.member.domain.model.*;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.exception.MemberErrorCode;
import com.gomo.app.member.exception.MemberPolicyViolationException;
import com.gomo.app.member.infrastructure.JwtSessionRedisService;
import com.gomo.app.member.infrastructure.oauth.OAuthProvider;
import com.gomo.app.member.infrastructure.oauth.OAuthProviderFactory;
import com.gomo.app.member.presentation.response.CreateMemberResponse;
import com.gomo.app.member.presentation.response.LoginMemberResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@ApplicationService
public class OAuthUseCase {

    private final OAuthProviderFactory providerFactory;
    private final MemberRepository memberRepository;
    private final JwtSessionRedisService jwtSessionRedisService;
    private final JwtUtil jwtUtil;

    @Transactional
    public LoginMemberResponse login(String providerName, String code){
        OAuthProvider provider = providerFactory.getProvider(providerName);
        OAuthUserInfo userInfo = provider.authenticate(code);

        Member member = memberRepository.findByEmail(Email.of(userInfo.getEmail()))
                .orElseGet(() -> createMember(userInfo, providerName));

        switch(member.getActivateStatus()){
			case DELETED -> throw new MemberPolicyViolationException(MemberErrorCode.MEMBER_DELETED, "member info has been deleted. check email or password");
			case BLOCKED -> throw new MemberPolicyViolationException(MemberErrorCode.MEMBER_BANNED, "member info has been blocked. check email or password");
		}

        String accessToken = jwtUtil.generateAccessToken(member.getId());
		String refreshToken = jwtUtil.generateRefreshToken(member.getId());
		long refreshTokenExptime = jwtUtil.extractExpirationTime(refreshToken);

		jwtSessionRedisService.setRefreshToken(member.getId(), refreshToken);

        member.updateLastLoginDateTime(LocalDateTime.now());

		return LoginMemberResponse.of(member.getId(), accessToken, refreshToken, refreshTokenExptime);
    }

    private Member createMember(OAuthUserInfo userInfo, String providerName){
        UUID uuid = UUIDGenerator.generate();
        MemberId memberId = MemberId.of(uuid);
        Member member = Member.of(
                memberId,
                Email.of(userInfo.getEmail()),
                null,
                null,
                MemberName.of(userInfo.getName()),
                null,
                LoginProvider.valueOf(providerName.toUpperCase()));
        return memberRepository.save(member);
    }
}
