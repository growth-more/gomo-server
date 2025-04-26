package com.gomo.app.member.domain.service;

import com.gomo.app.common.DomainService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@DomainService
@RequiredArgsConstructor
public class PasswordService {

    private final PasswordEncoder passwordEncoder;

    public String encode(String rawPassword){
        return passwordEncoder.encode(rawPassword);
    }

    public boolean matches(String originPassword, String inputPassword){
        return passwordEncoder.matches(originPassword, inputPassword);
    }
}
