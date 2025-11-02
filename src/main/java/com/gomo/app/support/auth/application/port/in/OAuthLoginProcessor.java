package com.gomo.app.support.auth.application.port.in;

import java.util.Optional;

import com.gomo.app.support.auth.application.port.dto.OAuthTokenDto;

public interface OAuthLoginProcessor {

	Optional<OAuthTokenDto> login(String providerName, String code);
}
