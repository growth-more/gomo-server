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

    public Password encode(String rawPassword){
        Password password = Password.fromRaw(rawPassword);
        return Password.fromEncoded(passwordEncoder.encode(password.getPassword()));
    }

    public Password update(Password originPassword, String originRawPassword, String newRawPassword){
        boolean isMatched = matches(originRawPassword, originPassword);
        return encode(newRawPassword);
    }

    public boolean matches(String rawPassword, Password password){
        return passwordEncoder.matches(rawPassword, password.getPassword());
    }
}
