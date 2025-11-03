package com.gomo.app.core.auth.application.port.out;

import java.util.Optional;
import java.util.UUID;

public interface PrincipalOAuthLoginProcessor {

	Optional<UUID> login(String email);
}
