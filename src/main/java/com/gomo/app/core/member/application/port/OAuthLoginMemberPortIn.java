package com.gomo.app.core.member.application.port;

import java.util.Optional;
import java.util.UUID;

public interface OAuthLoginMemberPortIn {

	/**
	 * @return authenticated member id, or null if none
	 */
	Optional<UUID> oauthAuthenticate(String email);
}
