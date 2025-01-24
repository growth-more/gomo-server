package com.gomo.app.quest.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.quest.domain.model.RepeatQuestId;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class DeleteRepeatQuestUseCase {

	private final RepeatQuestRepository repeatQuestRepository;

	public void delete(MemberId memberId, RepeatQuestId repeatQuestId) {

	}
}
