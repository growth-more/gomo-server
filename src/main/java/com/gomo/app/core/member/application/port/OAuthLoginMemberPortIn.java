package com.gomo.app.core.member.application.port;

import java.util.Optional;
import java.util.UUID;

public interface OAuthLoginMemberPortIn {

	/**
	 * Authenticates a member using an email address obtained from an OAuth provider.
	 *
	 * @param email The email address provided by the OAuth identity provider.
	 * @return An {@code Optional} containing the member's id (UUID) if a corresponding member is found.
	 * 		   Returns an empty {@code Optional} if no member is associated with the provided email.
	 */
	Optional<UUID> oauthAuthenticate(String email);
}
