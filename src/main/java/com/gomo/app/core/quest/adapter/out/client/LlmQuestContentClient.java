package com.gomo.app.core.quest.adapter.out.client;

import java.util.List;
import java.util.stream.IntStream;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.quest.application.port.command.CreateQuestContentCommand;
import com.gomo.app.core.quest.application.port.dto.QuestContentDto;
import com.gomo.app.core.quest.application.port.out.QuestContentCreator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Adapter
class LlmQuestContentClient implements QuestContentCreator {

	private final GenerateTextPortIn generateTextPortIn;

	@Override
	public List<QuestContentDto> create(CreateQuestContentCommand command) {
		List<CreateQuestContentCommand.Subject> subjects = command.subjects();
		if (subjects.isEmpty() || command.count() <= 0) {
			log.debug("");
			return List.of();
		}

		/*
		 * todo nurdy: GeminiGenerateTextUseCase(기존 py 기반 llm 호출 서비스) 구현 후, GenerateTextUseCase 활용
		 */

		int subjectCount = subjects.size();
		return IntStream.range(0, command.count())
			.mapToObj(i -> subjects.get(i % subjectCount))
			.map(subject -> QuestContentDto.of(
				command.participantId(),
				subject.id(),
				subject.name(),
				command.questType(),
				subject.name() + " 임시 퀘스트"
			)).toList();
	}
}
