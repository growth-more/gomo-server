package com.gomo.app.core.auth.application.port.out;

public interface JwtVerifier {

	boolean verify(String token);

	String extractSubject(String token);

	long extractExpirationTime(String token);
}
