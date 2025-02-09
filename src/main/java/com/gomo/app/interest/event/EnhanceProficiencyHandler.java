package com.gomo.app.interest.event;

import org.springframework.context.event.EventListener;

import com.gomo.app.common.event.EventHandler;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.service.ProficiencyService;
import com.gomo.app.quest.domain.model.ScoreReward;
import com.gomo.app.quest.event.ScoreQuestCompletedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@EventHandler
public class EnhanceProficiencyHandler {

	private final ProficiencyService proficiencyService;

	@EventListener(ScoreQuestCompletedEvent.class)
	public void handle(ScoreQuestCompletedEvent event) {
		InterestId interestId = InterestId.of(event.getSubjectId().getId());
		ScoreReward scoreReward = event.getScoreReward();
		proficiencyService.adjust(interestId, scoreReward.getScore());

		log.info("[EnhanceProficiencyHandler] Processing ScoreQuestCompletedEvent with member id: {}, trace id: {}", event.getMemberId(), scoreReward.getTraceId());
	}
}
