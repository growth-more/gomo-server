package com.gomo.app.batch;

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
	public void onSkipInProcess(Member item, @NotNull Throwable t) {
		log.error("Skipping process due to an error. Skipped Member ID: {}", item.getId(), t);
	}
}
