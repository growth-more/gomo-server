package com.gomo.app.support.auth.application.port.out;

import java.util.UUID;

public interface PrincipalLoginProcessor {

	UUID login(String email, String password);
}
