package com.gomo.app.quest.application.adapter;

import java.util.List;

import com.gomo.app.common.Adapter;
import com.gomo.app.quest.application.port.CreateQuestContentPort;
import com.gomo.app.quest.application.port.command.CreateQuestContentCommand;
import com.gomo.app.quest.application.port.dto.QuestContentDto;

@Adapter
class LlmCreateQuestContentAdapter implements CreateQuestContentPort {

	@Override
	public List<QuestContentDto> create(CreateQuestContentCommand command) {
		// todo nurdy: 기존 py 기반 서빙 프로그램 마이그레이션 필요
		return null;
	}
}
