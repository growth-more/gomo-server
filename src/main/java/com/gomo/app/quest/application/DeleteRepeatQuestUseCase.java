package com.gomo.app.quest.application;

import java.util.UUID;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.quest.domain.model.RepeatQuestId;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class DeleteRepeatQuestUseCase {

	private final RepeatQuestRepository repeatQuestRepository;

	public void delete(UUID accessorId, RepeatQuestId repeatQuestId) {

	}
}
