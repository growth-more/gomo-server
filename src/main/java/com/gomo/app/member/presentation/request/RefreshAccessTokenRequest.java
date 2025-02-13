package com.gomo.app.member.presentation.request;

import com.gomo.app.member.domain.model.MemberId;
import lombok.Getter;

import java.util.UUID;

@Getter
public class RefreshAccessTokenRequest {

    private String refreshToken;
    private UUID memberId;

    private RefreshAccessTokenRequest(){
    }

    private RefreshAccessTokenRequest(UUID memberId, String refreshToken){
        this.memberId = memberId;
        this.refreshToken = refreshToken;
    }

    public static RefreshAccessTokenRequest of(UUID memberId, String refreshToken){
        return new RefreshAccessTokenRequest(memberId, refreshToken);
    }
}
