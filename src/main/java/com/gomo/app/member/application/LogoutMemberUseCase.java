package com.gomo.app.member.application;

import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.infrastructure.JwtSessionRedisService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class LogoutMemberUseCase {

	private final JwtSessionRedisService jwtSessionRedisService;

    public void logout(UUID memberId){
        jwtSessionRedisService.deleteRefreshToken(memberId);
    }
}
