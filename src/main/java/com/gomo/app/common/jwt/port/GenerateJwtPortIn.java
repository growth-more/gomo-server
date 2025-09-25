package com.gomo.app.common.jwt.port;

import java.util.UUID;

public interface GenerateJwtPortIn {

	String generateAccessToken(UUID subject);

	String generateRefreshToken(UUID subject);

	String generateTemporaryToken(String subject, long expiration);
}
