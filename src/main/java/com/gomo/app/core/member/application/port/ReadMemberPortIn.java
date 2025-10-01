package com.gomo.app.core.member.application.port;

import java.util.UUID;

import com.gomo.app.core.member.application.port.dto.MemberDto;
import com.gomo.app.core.member.exception.MemberNotFoundException;

public interface ReadMemberPortIn {

	/**
	 * @return member info
	 * @exception MemberNotFoundException if not found
	 */
	MemberDto find(UUID id);
}
