package com.gomo.app.member.domain.repository;

import java.util.Optional;

public interface EmailAuthCodeRepository {

	void save(String email, String authCode);

	Optional<String> findByEmail(String email);

	void delete(String email);
}
