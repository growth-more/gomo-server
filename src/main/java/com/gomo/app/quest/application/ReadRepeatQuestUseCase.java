package com.gomo.app.quest.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.quest.presentation.response.ListRepeatQuestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadRepeatQuestUseCase {

	private final RepeatQuestRepository repeatQuestRepository;

	public ListRepeatQuestResponse findAll(ParticipantId participantId) {
		return null;
	}
}
