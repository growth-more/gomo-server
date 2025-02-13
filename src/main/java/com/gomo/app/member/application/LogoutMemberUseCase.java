package com.gomo.app.member.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.repository.MemberRepository;
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
