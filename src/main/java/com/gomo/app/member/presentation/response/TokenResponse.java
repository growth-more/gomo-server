package com.gomo.app.member.presentation.response;

import java.util.UUID;

import lombok.Getter;

@Getter
public class TokenResponse {
    private UUID memberId;
    private String token;

    private TokenResponse(UUID memberId, String token) {
        this.memberId = memberId;
        this.token = token;
    }

    public static TokenResponse of(UUID memberId, String token) {
        return new TokenResponse(memberId, token);
    }
}
