package com.gomo.app.common.jwt.port;

public interface VerifyJwtPortIn {

	boolean validateToken(String token);

	String extractSubject(String token);

	long extractExpirationTime(String token);
}
