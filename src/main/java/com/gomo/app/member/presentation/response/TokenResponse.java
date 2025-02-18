package com.gomo.app.member.presentation.response;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TokenResponse {
    private UUID id;
    private String token;

    private TokenResponse(UUID id, String token) {
        this.id = id;
        this.token = token;
    }

    public static TokenResponse of(UUID id, String token) {
        return new TokenResponse(id, token);
    }
}
