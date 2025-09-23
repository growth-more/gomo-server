package com.gomo.app.member.application.port;

import java.util.UUID;

import com.gomo.app.member.application.port.dto.MemberDto;

public interface ReadMemberPortIn {

	MemberDto find(UUID id);
}
