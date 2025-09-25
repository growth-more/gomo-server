package com.gomo.app.support.auth.domain.repository;

import java.util.Optional;

public interface AuthCodeRepository {

	void save(String email, String authCode);

	Optional<String> findByEmail(String email);

	void delete(String email);
}
