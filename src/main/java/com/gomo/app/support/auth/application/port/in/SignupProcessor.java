package com.gomo.app.support.auth.application.port.in;

import java.util.UUID;

import com.gomo.app.support.auth.application.port.command.CreatePrincipalCommand;

public interface SignupProcessor {

	UUID signup(CreatePrincipalCommand command);
}
