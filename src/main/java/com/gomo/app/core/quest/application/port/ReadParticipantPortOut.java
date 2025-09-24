package com.gomo.app.core.quest.application.port;

import java.util.UUID;

import com.gomo.app.core.quest.application.port.dto.ParticipantDto;

public interface ReadParticipantPortOut {

	ParticipantDto find(UUID participantId);
}
