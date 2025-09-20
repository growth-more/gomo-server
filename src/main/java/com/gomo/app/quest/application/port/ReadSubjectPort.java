package com.gomo.app.quest.application.port;

import java.util.List;

import com.gomo.app.quest.application.port.command.ReadSubjectCommand;
import com.gomo.app.quest.application.port.dto.SubjectDto;

public interface ReadSubjectPort {

	List<SubjectDto> findAll(ReadSubjectCommand command);
}
