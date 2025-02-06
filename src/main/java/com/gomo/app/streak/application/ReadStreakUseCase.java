package com.gomo.app.streak.application;

import java.time.LocalDate;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.streak.domain.model.AchieverId;
import com.gomo.app.streak.domain.model.StreakType;
import com.gomo.app.streak.domain.service.StreakService;
import com.gomo.app.streak.presentation.response.ListStreakResponse;
import com.gomo.app.streak.presentation.response.ReadStreakResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadStreakUseCase {

	private final StreakService streakService;

	public ListStreakResponse findAll(AchieverId achieverId, LocalDate startDate, LocalDate endDate) {
		List<ReadStreakResponse> dailyStreaks = findStreaksByType(achieverId, StreakType.DAILY, startDate, endDate);
		List<ReadStreakResponse> weeklyStreaks = findStreaksByType(achieverId, StreakType.WEEKLY, startDate, endDate);
		List<ReadStreakResponse> monthlyStreaks = findStreaksByType(achieverId, StreakType.MONTHLY, startDate, endDate);

		return ListStreakResponse.of(dailyStreaks, weeklyStreaks, monthlyStreaks);
	}

	@NotNull
	private List<ReadStreakResponse> findStreaksByType(AchieverId achieverId, StreakType streakType, LocalDate startDate, LocalDate endDate) {
		return streakService.findAllByStreakType(achieverId, streakType, startDate, endDate).stream()
			.map(ReadStreakResponse::of)
			.toList();
	}
}
