package com.gomo.app.member.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.util.JwtUtil;
import com.gomo.app.member.infrastructure.JwtSessionRedisService;
import com.gomo.app.member.presentation.request.RefreshAccessTokenRequest;
import com.gomo.app.member.presentation.response.LoginMemberResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class RefreshAccessTokenUseCase {

    private final JwtSessionRedisService jwtSessionRedisService;

    public LoginMemberResponse refresh(RefreshAccessTokenRequest request){
        return null;
    }
}
