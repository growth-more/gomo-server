package com.gomo.app.quest.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.quest.presentation.response.ListRepeatQuestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadRepeatQuestUseCase {

	private final RepeatQuestRepository repeatQuestRepository;

	public ListRepeatQuestResponse findAll(MemberId memberId, QuestType questType) {
		return null;
	}
}
