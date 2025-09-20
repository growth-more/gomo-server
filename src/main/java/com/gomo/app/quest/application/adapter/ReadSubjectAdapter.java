package com.gomo.app.quest.application.adapter;

import java.util.List;

import com.gomo.app.common.Adapter;
import com.gomo.app.quest.application.port.ReadSubjectPort;
import com.gomo.app.quest.application.port.command.ReadSubjectCommand;
import com.gomo.app.quest.application.port.dto.SubjectDto;

@Adapter
class ReadSubjectAdapter implements ReadSubjectPort {

	@Override
	public List<SubjectDto> findAll(ReadSubjectCommand command) {
		// todo jhl221123: 관심사 모듈과 연동
		return null;
	}
}
