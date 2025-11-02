package com.gomo.app.support.auth.application.port.in;

import com.gomo.app.support.auth.application.port.dto.AuthTokenDto;

public interface RefreshTokenUpdater {

	AuthTokenDto update(String refreshToken);
}
