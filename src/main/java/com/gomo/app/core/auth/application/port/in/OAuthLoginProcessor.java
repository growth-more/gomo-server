package com.gomo.app.core.auth.application.port.in;

import java.util.Optional;

import com.gomo.app.core.auth.application.port.dto.OAuthTokenDto;

public interface OAuthLoginProcessor {

	Optional<OAuthTokenDto> login(String providerName, String code);
}
