package com.gomo.app.support.auth.application.port;

import java.util.UUID;

public interface DeleteAuthTokenPortIn {

	void deleteRefreshToken(UUID principalId);
}
