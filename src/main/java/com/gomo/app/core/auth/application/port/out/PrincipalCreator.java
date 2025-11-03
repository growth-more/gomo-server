package com.gomo.app.core.auth.application.port.out;

import java.util.UUID;

import com.gomo.app.core.auth.application.port.command.CreatePrincipalCommand;

public interface PrincipalCreator {

	UUID create(CreatePrincipalCommand command);
}
