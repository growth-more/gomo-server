package com.gomo.app.quest.application.adapter;

import java.util.List;

import com.gomo.app.common.Adapter;
import com.gomo.app.llm.application.GenerateTextUseCase;
import com.gomo.app.quest.application.port.CreateQuestContentPort;
import com.gomo.app.quest.application.port.command.CreateQuestContentCommand;
import com.gomo.app.quest.application.port.dto.QuestContentDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class LlmCreateQuestContentAdapter implements CreateQuestContentPort {

	private final GenerateTextUseCase generateTextUseCase;

	@Override
	public List<QuestContentDto> create(CreateQuestContentCommand command) {
		List<CreateQuestContentCommand.Subject> subjects = command.subjects();

		/*
		 * todo nurdy: GeminiGenerateTextUseCase(기존 py 기반 llm 호출 서비스) 구현 후, GenerateTextUseCase 활용
		 */

		return subjects.stream().map(subject -> QuestContentDto.of(
				command.participantId(),
				subject.id(),
				subject.name(),
				command.questType(),
				"[Should replace] Quest related to " + subject.name()
			)
		).toList();
	}
}
