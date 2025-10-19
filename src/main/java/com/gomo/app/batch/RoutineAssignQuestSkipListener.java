package com.gomo.app.batch;

import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.SkipListener;

import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.quest.application.port.dto.ParticipantDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoutineAssignQuestSkipListener implements SkipListener<Member, ParticipantDto> {

	@Override
	public void onSkipInRead(@NotNull Throwable t) {
		log.error("Skipping read due to an error.", t);
	}

	@Override
	public void onSkipInProcess(Member item, @NotNull Throwable t) {
		log.error("Skipping process due to an error. Skipped member id: {}", item.getId(), t);
	}

	@Override
	public void onSkipInWrite(ParticipantDto item, @NotNull Throwable t) {
		log.error("Skipping write due to an error. Skipped participant id: {}", item.id(), t);
	}
}
