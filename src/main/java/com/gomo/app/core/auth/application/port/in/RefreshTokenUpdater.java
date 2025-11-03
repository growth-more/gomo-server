package com.gomo.app.core.auth.application.port.in;

import com.gomo.app.core.auth.application.port.dto.AuthTokenDto;

public interface RefreshTokenUpdater {

	AuthTokenDto update(String refreshToken);
}
