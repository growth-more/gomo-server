package com.gomo.app.core.auth.application.port.in;

import java.util.UUID;

import com.gomo.app.core.auth.application.port.command.CreatePrincipalCommand;

public interface SignupProcessor {

	UUID signup(CreatePrincipalCommand command);
}
