package com.gomo.app.core.member.application.port;

import java.util.UUID;

import com.gomo.app.core.member.application.port.dto.MemberDto;

public interface ReadMemberPortIn {

	MemberDto find(UUID id);
}
