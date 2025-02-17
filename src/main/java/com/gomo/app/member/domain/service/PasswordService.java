package com.gomo.app.member.domain.service;

import com.gomo.app.common.domain.service.DomainService;
import com.gomo.app.member.domain.model.Password;
import com.gomo.app.member.exception.MemberAuthenticationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.gomo.app.member.exception.MemberErrorCode.AUTHENTICATION_FAILED;

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
