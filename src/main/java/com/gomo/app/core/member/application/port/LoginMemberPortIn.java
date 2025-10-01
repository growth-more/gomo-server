package com.gomo.app.core.member.application.port;

import java.util.UUID;

public interface LoginMemberPortIn {

	/**
	 * @return authenticated member id
	 */
	UUID authenticate(String email, String password);
}
