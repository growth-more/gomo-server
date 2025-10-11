package com.gomo.app.core.streak.application.usecase;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.streak.application.port.dto.ListStreakDto;
import com.gomo.app.core.streak.application.port.dto.StreakDto;
import com.gomo.app.core.streak.domain.model.StreakType;
import com.gomo.app.core.streak.domain.service.StreakService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadStreakUseCase {

	private final StreakService streakService;

	public ListStreakDto findAll(UUID achieverId, LocalDate startDate, LocalDate endDate) {
		List<StreakDto> dailyStreaks = findStreaksByType(achieverId, StreakType.DAILY, startDate, endDate);
		List<StreakDto> weeklyStreaks = findStreaksByType(achieverId, StreakType.WEEKLY, startDate, endDate);
		List<StreakDto> monthlyStreaks = findStreaksByType(achieverId, StreakType.MONTHLY, startDate, endDate);
		return ListStreakDto.of(dailyStreaks, weeklyStreaks, monthlyStreaks);
	}

	@NotNull
	private List<StreakDto> findStreaksByType(UUID achieverId, StreakType streakType, LocalDate startDate, LocalDate endDate) {
		return streakService.findAllByStreakType(achieverId, streakType, startDate, endDate).stream()
			.map(StreakDto::from)
			.toList();
	}
}
