package com.gomo.app.member.application.port;

import java.time.LocalDate;
import java.util.List;

import com.gomo.app.member.application.port.dto.ActiveMemberDto;

public interface ReadActiveMemberPortIn {

	List<ActiveMemberDto> findAll(LocalDate lastLoginDate);
}
