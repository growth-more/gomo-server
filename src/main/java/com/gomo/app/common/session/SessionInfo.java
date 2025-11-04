package com.gomo.app.common.session;

import java.util.UUID;

import lombok.Getter;

@Getter
public class SessionInfo {

	public static final String SESSION_PRINCIPAL_ID = "principalId";

	private UUID principalId;

	private SessionInfo(UUID principalId) {
		this.principalId = principalId;
	}

	public static SessionInfo of(UUID principalId) {
		return new SessionInfo(principalId);
	}
}
