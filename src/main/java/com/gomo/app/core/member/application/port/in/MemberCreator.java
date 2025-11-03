package com.gomo.app.core.member.application.port.in;

import java.util.UUID;

import com.gomo.app.core.member.application.port.command.CreateMemberCommand;
import com.gomo.app.core.member.domain.exception.EmailDuplicatedException;
import com.gomo.app.core.member.domain.exception.HandleDuplicatedException;

public interface MemberCreator {

	/**
	 * Creates a new member, validates their information, and publishes an event upon successful creation.
	 *
	 * @param command A {@link CreateMemberCommand} object containing all necessary information for member.
	 * @return The {@link UUID} of the newly created member.
	 * @throws EmailDuplicatedException if the provided email is already registered.
	 * @throws HandleDuplicatedException if the provided handle is already in use.
	 */
	UUID create(CreateMemberCommand command);
}
