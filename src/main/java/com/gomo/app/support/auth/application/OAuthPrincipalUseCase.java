package com.gomo.app.support.auth.application;

import java.util.Optional;
import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.jwt.port.VerifyJwtPortIn;
import com.gomo.app.core.member.application.port.OAuthLoginMemberPortIn;
import com.gomo.app.core.member.domain.model.OAuthUserInfo;
import com.gomo.app.support.auth.domain.model.AuthToken;
import com.gomo.app.support.auth.infrastructure.oauth.OAuthProviderFactory;
import com.gomo.app.support.auth.infrastructure.oauth.provider.OAuthProvider;
import com.gomo.app.support.auth.presentation.response.OAuthTokenResponse;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class OAuthPrincipalUseCase {

	private final OAuthProviderFactory providerFactory;
	private final OAuthLoginMemberPortIn oAuthLoginMemberPortIn;
	private final CreateAuthTokenUseCase createAuthTokenUseCase;
	private final VerifyJwtPortIn verifyJwtPortIn;

	@AuditLog(action = "OAUTH_PRINCIPAL")
	public OAuthTokenResponse getUserInformation(String providerName, String code) {
		OAuthProvider provider = providerFactory.getProvider(providerName);
		OAuthUserInfo userInfo = provider.authenticate(code);
		Optional<UUID> uuidOpt = oAuthLoginMemberPortIn.oauthAuthenticate(userInfo.getEmail());
		if (uuidOpt.isEmpty()) {
			return OAuthTokenResponse.of(null, null, 0, userInfo);
		}
		UUID principalId = uuidOpt.get();
		AuthToken authToken = createAuthTokenUseCase.create(principalId);
		long refreshExpirationTime = verifyJwtPortIn.extractExpirationTime(authToken.getRefreshToken());
		return OAuthTokenResponse.of(principalId, authToken, refreshExpirationTime, userInfo);
	}
}
