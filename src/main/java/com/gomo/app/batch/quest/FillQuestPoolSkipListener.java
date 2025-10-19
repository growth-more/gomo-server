package com.gomo.app.batch.quest;

import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.SkipListener;

import com.gomo.app.core.member.domain.model.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FillQuestPoolSkipListener implements SkipListener<Member, Member> {

	@Override
	public void onSkipInRead(@NotNull Throwable t) {
		log.error("Skipping read due to an error.", t);
	}

	@Override
	public void onSkipInWrite(Member item, @NotNull Throwable t) {
		log.error("Skipping write due to an error. Skipped member id: {}", item.getId(), t);
	}
}
