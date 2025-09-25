package com.gomo.app.core.member.application.port;

import java.util.Optional;
import java.util.UUID;

public interface OAuthLoginMemberPortIn {

	Optional<UUID> oauthAuthenticate(String email);
}
