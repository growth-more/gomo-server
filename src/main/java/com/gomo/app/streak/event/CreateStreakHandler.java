package com.gomo.app.streak.event;

import org.springframework.context.event.EventListener;

import com.gomo.app.common.event.EventHandler;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.quest.event.StreakQuestCompletedEvent;
import com.gomo.app.streak.domain.model.AchieverId;
import com.gomo.app.streak.domain.model.Streak;
import com.gomo.app.streak.domain.model.StreakId;
import com.gomo.app.streak.domain.model.StreakType;
import com.gomo.app.streak.domain.service.StreakService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EventHandler
public class CreateStreakHandler {

	private final StreakService streakService;

	@EventListener(StreakQuestCompletedEvent.class)
	public void handle(StreakQuestCompletedEvent event) {
		Streak streak = Streak.of(
			StreakId.of(UUIDGenerator.generate()),
			AchieverId.of(event.getParticipantId().getId()),
			StreakType.valueOf(event.getQuestType().name()),
			event.getQuestCompletedDateTime().toLocalDate(),
			1
		);

		streakService.fill(streak);
	}
}
