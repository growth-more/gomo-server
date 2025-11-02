package com.gomo.app.support.auth.application.port.in;

import com.gomo.app.support.auth.application.port.dto.AuthTokenDto;

public interface LoginProcessor {

	AuthTokenDto login(String email, String password);
}
