package com.gomo.app.core.member.adapter.in.api.request;

import java.util.UUID;

import com.gomo.app.core.member.application.port.command.UpdateQuestPropertyCommand;

import lombok.Getter;

@Getter
public class UpdateQuestPropertyRequest {

	private final int dailyThreshold;
	private final int weeklyThreshold;
	private final int monthlyThreshold;

	private UpdateQuestPropertyRequest(int dailyThreshold, int weeklyThreshold, int monthlyThreshold) {
		this.dailyThreshold = dailyThreshold;
		this.weeklyThreshold = weeklyThreshold;
		this.monthlyThreshold = monthlyThreshold;
	}

	public static UpdateQuestPropertyRequest of(int dailyThreshold, int weeklyThreshold, int monthlyThreshold) {
		return new UpdateQuestPropertyRequest(dailyThreshold, weeklyThreshold, monthlyThreshold);
	}

	public UpdateQuestPropertyCommand toCommand(UUID memberId) {
		return UpdateQuestPropertyCommand.of(memberId, dailyThreshold, weeklyThreshold, monthlyThreshold);
	}
}
