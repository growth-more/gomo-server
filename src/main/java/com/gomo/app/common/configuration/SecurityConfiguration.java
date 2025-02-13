package com.gomo.app.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration {
    // todo: 추후 Spring Security 적용 시 본 class 에서 적용하면됨.
    @Bean
    public PasswordEncoder passwordEncoder() {
        // todo: Deploy 환경에서는 strength 12 권장.
        return new BCryptPasswordEncoder(10);
    }
}
