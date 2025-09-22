package com.gomo.app.member.application.port;

import java.time.LocalDate;
import java.util.List;

import com.gomo.app.member.application.port.dto.MemberDto;

public interface ReadActiveMemberPortIn {

	List<MemberDto> findAll(LocalDate lastLoginDate);
}
