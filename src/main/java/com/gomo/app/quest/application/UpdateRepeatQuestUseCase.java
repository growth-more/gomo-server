package com.gomo.app.quest.application;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.quest.domain.model.RepeatQuestId;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.quest.presentation.request.UpdateRepeatQuestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateRepeatQuestUseCase {

	private final RepeatQuestRepository repeatQuestRepository;

	public void update(MemberId memberId, RepeatQuestId repeatQuestId, UpdateRepeatQuestRequest request) {

	}
}
