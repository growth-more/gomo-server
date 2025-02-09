package com.gomo.app.streak.event;

import java.util.UUID;

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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@EventHandler
public class CreateStreakHandler {

	private final StreakService streakService;

	@EventListener(StreakQuestCompletedEvent.class)
	public void handle(StreakQuestCompletedEvent event) {
		UUID participantId = event.getParticipantId().getId();
		Streak streak = Streak.of(
			StreakId.of(UUIDGenerator.generate()),
			AchieverId.of(participantId),
			StreakType.valueOf(event.getQuestType().name()),
			event.getQuestCompletedDateTime().toLocalDate(),
			1
		);

		streakService.fill(streak);
		log.info("[CreateStreakHandler] Processing StreakQuestCompletedEvent with member id: {}, trace id: {}", participantId, event.getTraceId());
	}
}
