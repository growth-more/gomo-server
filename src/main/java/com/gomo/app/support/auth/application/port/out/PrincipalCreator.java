package com.gomo.app.support.auth.application.port.out;

import java.util.UUID;

import com.gomo.app.support.auth.application.port.command.CreatePrincipalCommand;

public interface PrincipalCreator {

	UUID create(CreatePrincipalCommand command);
}
