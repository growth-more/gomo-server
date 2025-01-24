package com.gomo.app.streak.event;

import org.springframework.context.event.EventListener;

import com.gomo.app.common.event.EventHandler;
import com.gomo.app.quest.event.StreakQuestCompletedEvent;
import com.gomo.app.streak.domain.service.StreakService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EventHandler
public class CreateStreakHandler {

	private StreakService streakService;

	@EventListener(StreakQuestCompletedEvent.class)
	public void handle(StreakQuestCompletedEvent event) {

	}
}
