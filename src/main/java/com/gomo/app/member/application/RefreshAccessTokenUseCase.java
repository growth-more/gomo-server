package com.gomo.app.member.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.util.JwtUtil;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.exception.MemberAuthenticationFailedException;
import com.gomo.app.member.infrastructure.JwtSessionRedisService;
import com.gomo.app.member.presentation.response.LoginMemberResponse;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static com.gomo.app.member.exception.MemberErrorCode.AUTHENTICATION_FAILED;

@RequiredArgsConstructor
@ApplicationService
public class RefreshAccessTokenUseCase {

    private final JwtUtil jwtUtil;
    private final JwtSessionRedisService jwtSessionRedisService;

    public LoginMemberResponse refresh(String refreshToken){
        if (refreshToken == null){
            throw new MemberAuthenticationFailedException(AUTHENTICATION_FAILED, "Member Authentication fail");
        }

        MemberId memberId = MemberId.of(UUID.fromString(jwtUtil.extractMemberId(refreshToken)));

        String storedRefreshToken = jwtSessionRedisService.getRefreshToken(memberId);

        if(!storedRefreshToken.equals(refreshToken)){
            throw new MemberAuthenticationFailedException(AUTHENTICATION_FAILED, "Member Authentication failed");
        }

        String newAccessToken = jwtUtil.generateAccessToken(memberId);
        String newRefreshToken = jwtUtil.generateRefreshToken(memberId);
        long refreshTokenExpiry = jwtUtil.extractExpirationTime(newRefreshToken);

        jwtSessionRedisService.updateRefreshToken(memberId, newRefreshToken);

        return LoginMemberResponse.of(memberId, newAccessToken, newRefreshToken, refreshTokenExpiry);
    }
}
