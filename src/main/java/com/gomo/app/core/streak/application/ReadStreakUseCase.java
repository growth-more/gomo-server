package com.gomo.app.core.streak.application;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.core.streak.domain.model.AchieverId;
import com.gomo.app.core.streak.domain.model.StreakType;
import com.gomo.app.core.streak.domain.service.StreakService;
import com.gomo.app.core.streak.presentation.response.ListStreakResponse;
import com.gomo.app.core.streak.presentation.response.ReadStreakResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadStreakUseCase {

	private final StreakService streakService;

	public ListStreakResponse findAll(UUID achieverId, LocalDate startDate, LocalDate endDate) {
		AchieverId targetId = AchieverId.of(achieverId);
		List<ReadStreakResponse> dailyStreaks = findStreaksByType(targetId, StreakType.DAILY, startDate, endDate);
		List<ReadStreakResponse> weeklyStreaks = findStreaksByType(targetId, StreakType.WEEKLY, startDate, endDate);
		List<ReadStreakResponse> monthlyStreaks = findStreaksByType(targetId, StreakType.MONTHLY, startDate, endDate);
		return ListStreakResponse.of(dailyStreaks, weeklyStreaks, monthlyStreaks);
	}

	@NotNull
	private List<ReadStreakResponse> findStreaksByType(AchieverId achieverId, StreakType streakType, LocalDate startDate, LocalDate endDate) {
		return streakService.findAllByStreakType(achieverId, streakType, startDate, endDate).stream()
			.map(ReadStreakResponse::of)
			.toList();
	}
}
