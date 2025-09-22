package com.gomo.app.quest.application.port;

import java.time.LocalDate;
import java.util.List;

import com.gomo.app.quest.application.port.dto.ActiveParticipantDto;

public interface ReadActiveParticipantPortOut {

	List<ActiveParticipantDto> findAll(LocalDate lastLoginDate);
}
