package com.gomo.app.core.auth.application.port.in;

import com.gomo.app.core.auth.application.port.dto.AuthTokenDto;

public interface LoginProcessor {

	AuthTokenDto login(String email, String password);
}
