package com.gomo.app.batch;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.SkipListener;

import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoutineAssignQuestSkipListener implements SkipListener<Member, List<AssignQuest>> {

	@Override
	public void onSkipInRead(@NotNull Throwable t) {
		log.error("Skipping read due to an error.", t);
	}

	@Override
	public void onSkipInWrite(List<AssignQuest> item, @NotNull Throwable t) {
		log.error("Skipping write due to an error. Number of quests in the skipped list: {}", item.size(), t);
	}

	@Override
	public void onSkipInProcess(Member item, @NotNull Throwable t) {
		log.error("Skipping process due to an error. Skipped Member ID: {}", item.getId(), t);
	}
}
