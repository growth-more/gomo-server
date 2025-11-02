package com.gomo.app.support.auth.application.port.out;

import java.util.Optional;
import java.util.UUID;

public interface PrincipalOAuthLoginProcessor {

	Optional<UUID> login(String email);
}
