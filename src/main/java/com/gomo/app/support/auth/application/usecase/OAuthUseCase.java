package com.gomo.app.support.auth.application.usecase;

import java.util.Optional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.common.security.jwt.application.port.VerifyJwtPortIn;
import com.gomo.app.core.member.application.port.OAuthLoginMemberPortIn;
import com.gomo.app.support.auth.application.port.dto.OAuthTokenDto;
import com.gomo.app.support.auth.domain.model.AuthToken;
import com.gomo.app.support.auth.domain.model.OAuthPrincipal;
import com.gomo.app.support.auth.infrastructure.oauth.OAuthProviderFactory;
import com.gomo.app.support.auth.infrastructure.oauth.provider.OAuthProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class OAuthUseCase {

	private final OAuthProviderFactory providerFactory;
	private final OAuthLoginMemberPortIn oAuthLoginMemberPortIn;
	private final CreateAuthTokenInternalService createAuthTokenInternalService;
	private final VerifyJwtPortIn verifyJwtPortIn;

	@AuditLog(action = "FIND_OAUTH_PRINCIPAL")
	public Optional<OAuthTokenDto> findPrincipal(String providerName, String code) {
		OAuthProvider provider = providerFactory.getProvider(providerName);
		OAuthPrincipal principal = provider.authenticate(code);

		return oAuthLoginMemberPortIn.oauthAuthenticate(principal.getEmail())
			.map(principalId -> {
				AuthToken authToken = createAuthTokenInternalService.create(principalId);
				long refreshExpirationTime = verifyJwtPortIn.extractExpirationTime(authToken.getRefreshToken());
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
