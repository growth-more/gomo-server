package com.gomo.app.core.member.application.port;

import java.util.UUID;

public interface LoginMemberPortIn {

	UUID authenticate(String email, String password);
}
