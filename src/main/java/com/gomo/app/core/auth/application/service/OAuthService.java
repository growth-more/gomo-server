package com.gomo.app.core.auth.application.service;

import java.util.Optional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.auth.application.port.dto.OAuthTokenDto;
import com.gomo.app.core.auth.application.port.in.OAuthLoginProcessor;
import com.gomo.app.core.auth.application.port.out.JwtVerifier;
import com.gomo.app.core.auth.application.port.out.OAuthProvider;
import com.gomo.app.core.auth.application.port.out.OAuthProviderReader;
import com.gomo.app.core.auth.application.port.out.PrincipalOAuthLoginProcessor;
import com.gomo.app.core.auth.domain.model.AuthToken;
import com.gomo.app.core.auth.domain.model.OAuthPrincipal;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class OAuthService implements OAuthLoginProcessor {

	private final OAuthProviderReader providerFactory;
	private final PrincipalOAuthLoginProcessor principalOAuthLoginProcessor;
	private final AuthTokenService authTokenService;
	private final JwtVerifier jwtVerifier;

	@Override
	@AuditLog(action = "OAUTH_LOGIN")
	public Optional<OAuthTokenDto> login(String providerName, String code) {
		OAuthProvider provider = providerFactory.read(providerName);
		OAuthPrincipal principal = provider.authenticate(code);

		return principalOAuthLoginProcessor.login(principal.getEmail())
			.map(principalId -> {
				AuthToken authToken = authTokenService.create(principalId);
				long refreshExpirationTime = jwtVerifier.extractExpirationTime(authToken.getRefreshToken());
				return OAuthTokenDto.withToken(
					principalId,
					authToken.getAccessToken(),
					authToken.getRefreshToken(),
					refreshExpirationTime,
					principal.getProvider().name(),
					principal.getEmail(),
					principal.getName()
				);
			})
			.or(() -> Optional.of(
				OAuthTokenDto.withoutToken(
					principal.getProvider().name(),
					principal.getEmail(),
					principal.getName()
				)
			));
	}
}
