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

    private final RestClient restClient;
    private final MemberRepository memberRepository;
    private final JwtSessionRedisService jwtSessionRedisService;
    private final JwtUtil jwtUtil;

    @Value("${oauth.google.client-id}")
    private String clientId;

    @Value("${oauth.google.client-secret}")
    private String clientSecret;

    @Value("${oauth.google.redirect-uri}")
    private String redirectUri;

    @Value("${oauth.google.token-uri}")
    private String tokenUri;

    @Value("${oauth.google.user-info-uri}")
    private String userInfoUri;

    @Transactional
    public LoginMemberResponse login(String code){
        String googleAccessToken = getAccessToken(code);
        JsonNode userInfo = getUserResource(googleAccessToken);

        Member member = memberRepository.findByEmail(Email.of(userInfo.get("email").asText()))
                .orElseGet(() -> createMember(userInfo, code));

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

    private Member createMember(JsonNode userInfo, String code){
        UUID uuid = UUIDGenerator.generate();
        MemberId memberId = MemberId.of(uuid);
        String email = userInfo.get("email").asText();
        String name = userInfo.get("name").asText();
        Member member = Member.of(memberId, Email.of(email), null, null, MemberName.of(name), null, LoginProvider.GOOGLE);
        return memberRepository.save(member);
    }

    private String getAccessToken(String code){
        ResponseEntity<JsonNode> response = restClient.post()
                .uri(tokenUri)
                .body(Map.of(
                        "code", code,
                        "client_id", clientId,
                        "client_secret", clientSecret,
                        "redirect_uri", redirectUri,
                        "grant_type", "authorization_code"
                )).retrieve()
                .toEntity(JsonNode.class);
        JsonNode accessToken = response.getBody();
        return accessToken.get("access_token").asText();
    }

    private JsonNode getUserResource(String accessToken){
        ResponseEntity<JsonNode> response = restClient.get()
                .uri(userInfoUri)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .toEntity(JsonNode.class);
        return response.getBody();
    }
}
