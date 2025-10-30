package com.gomo.app.core.member.application.port.in;

import java.util.UUID;

import com.gomo.app.core.member.application.port.command.CreateMemberCommand;
import com.gomo.app.core.member.domain.exception.EmailDuplicatedException;
import com.gomo.app.core.member.domain.exception.HandleDuplicatedException;

public interface MemberCreator {

	/**
	 * Creates a new member, validates their information, and publishes an event upon successful creation.
	 * This process requires a valid email token from a prior email verification step.
	 *
	 * @param command A {@link CreateMemberCommand} object containing all necessary information for member registration,
	 *                such as email, handle, password, and an email verification token.
	 * @return The {@link UUID} of the newly created member.
	 * @throws IllegalArgumentException if the provided email token for email verification is invalid.
	 * @throws EmailDuplicatedException if the provided email is already registered.
	 * @throws HandleDuplicatedException if the provided handle is already in use.
	 */
	UUID create(CreateMemberCommand command);
}
