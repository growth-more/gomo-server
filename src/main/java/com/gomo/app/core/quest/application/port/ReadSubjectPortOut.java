package com.gomo.app.core.quest.application.port;

import java.util.List;
import java.util.UUID;

import com.gomo.app.core.quest.application.port.dto.SubjectDto;

public interface ReadSubjectPortOut {

	List<SubjectDto> findAll(UUID participantId);
}
