package com.gomo.app.interest.event;

import org.springframework.context.event.EventListener;

import com.gomo.app.common.event.EventHandler;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.domain.repository.ScoreThresholdRepository;
import com.gomo.app.quest.event.ScoreQuestCompletedEvent;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EventHandler
public class EnhanceProficiencyHandler {

	private final InterestRepository interestRepository;
	private final ScoreThresholdRepository scoreThresholdRepository;

	@EventListener(ScoreQuestCompletedEvent.class)
	public void handle(ScoreQuestCompletedEvent event) {

	}
}
