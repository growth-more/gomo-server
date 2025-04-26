package com.gomo.app.member.application;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.infrastructure.JwtSessionRedisService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class LogoutMemberUseCase {

	private final JwtSessionRedisService jwtSessionRedisService;

    public void logout(MemberId memberId){
        jwtSessionRedisService.deleteRefreshToken(memberId);
    }
}
