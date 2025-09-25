package com.gomo.app.common.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.jwt.port.GenerateJwtPortIn;
import com.gomo.app.common.jwt.port.VerifyJwtPortIn;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationService
class ManageJwtUseCase implements GenerateJwtPortIn, VerifyJwtPortIn {
	private final long accessExpirationTime;
	private final long refreshExpirationTime;
	private final SecretKey secretKey;

	public ManageJwtUseCase(
		@Value("${jwt.secret-key}") String secret,
		@Value("${jwt.expiration.access}") long accessExpirationTime,
		@Value("${jwt.expiration.refresh}") long refreshExpirationTime) {
		this.accessExpirationTime = accessExpirationTime;
		this.refreshExpirationTime = refreshExpirationTime;
		this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
	}

	@Override
	public String generateAccessToken(UUID subject) {
		return generateToken(subject.toString(), accessExpirationTime);
	}

	@Override
	public String generateRefreshToken(UUID subject) {
		return generateToken(subject.toString(), refreshExpirationTime);
	}

	@Override
	public String generateTemporaryToken(String subject, long expiration) {
		return generateToken(subject, expiration);
	}

	@Override
	public boolean validateToken(String token) {
		try {
			parseClaims(token);
			return true;
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT token: {}", token);
			return false;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	@Override
	public String extractSubject(String token) {
		return parseClaims(token).getSubject();
	}

	@Override
	public long extractExpirationTime(String token) {
		Claims claims = parseClaims(token);
		Date expiration = claims.getExpiration();
		return expiration.getTime() / 1000;
	}

	private String generateToken(String subject, long expirationTime) {
		Date exp = new Date(System.currentTimeMillis() + (expirationTime * 1000));
		return Jwts.builder()
			.subject(subject)
			.issuedAt(new Date())
			.expiration(exp)
			.signWith(this.secretKey)
			.compact();
	}

	private Claims parseClaims(String token) {
		return Jwts.parser()
			.verifyWith(this.secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}
}
