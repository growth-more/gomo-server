package com.gomo.app.quest.application.port;

import java.util.List;

import com.gomo.app.quest.application.port.dto.ActiveParticipantDto;

public interface ReadActiveParticipantPort {

	List<ActiveParticipantDto> findAll();
}
