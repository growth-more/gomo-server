package com.gomo.app.member.domain.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.gomo.app.common.DomainService;

import lombok.RequiredArgsConstructor;

// TODO <jhl221123> to <nurdy>: 해당 클래스 테스트를 작성해야합니다.
@RequiredArgsConstructor
@DomainService
public class PasswordService {

    private final PasswordEncoder passwordEncoder;

    public String encode(String rawPassword){
        return passwordEncoder.encode(rawPassword);
    }

    public boolean matches(String originPassword, String inputPassword){
        return passwordEncoder.matches(originPassword, inputPassword);
    }
}
