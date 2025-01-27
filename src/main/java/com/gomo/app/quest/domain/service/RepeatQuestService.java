package com.gomo.app.quest.domain.service;

import java.util.List;

import com.gomo.app.common.domain.service.DisplayOrder;
import com.gomo.app.common.domain.service.DomainService;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.model.QuestProperty;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class RepeatQuestService {

	private final RepeatQuestRepository repeatQuestRepository;

	public RepeatQuest create(RepeatQuest repeatQuest,  QuestProperty questProperty) {
		return null;
	}

	public List<RepeatQuest> findAllByQuestType(MemberId memberId, QuestType questType) {
		return null;
	}

	public DisplayOrder findNextDisplayOrder(MemberId memberId, QuestType questType) {
		return null;
	}
}
