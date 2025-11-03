package com.gomo.app.core.streak.application.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.streak.application.port.dto.ListStreakDto;
import com.gomo.app.core.streak.application.port.dto.StreakDto;
import com.gomo.app.core.streak.application.port.in.StreakCreator;
import com.gomo.app.core.streak.application.port.in.StreakReader;
import com.gomo.app.core.streak.domain.model.Achiever;
import com.gomo.app.core.streak.domain.model.Streak;
import com.gomo.app.core.streak.domain.model.StreakType;
import com.gomo.app.core.streak.domain.repository.StreakRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class StreakService implements StreakCreator, StreakReader {

	private final AchieverService achieverService;
	private final StreakRepository streakRepository;

	@Override
	@AuditLog(action = "STREAK_CREATE")
	public void create(UUID achieverId, String streakType, LocalDate creationDate) {
		Streak streak = Streak.of(UUIDGenerator.generate(), achieverId, StreakType.valueOf(streakType), creationDate, 1);
		increaseStreakDays(streak);
		streakRepository.findByAchieverIdAndStreakTypeAndFilledDate(streak.getAchieverId(), streak.getStreakType(), streak.getFilledDate())
			.map(existingStreak -> {
				existingStreak.increaseCompletedQuestCount();
				return existingStreak;
			}).orElseGet(() -> createInitialStreak(streak));
	}

	private void increaseStreakDays(Streak streak) {
		Achiever achiever = achieverService.findById(streak.getAchieverId());
		List<Streak> priorDayStreaks = streakRepository.findByAchieverIdAndFilledDate(streak.getAchieverId(), streak.getFilledDate().minusDays(1));
		boolean isFilledPriorDay = !priorDayStreaks.isEmpty();
		achiever.updateStreakDays(isFilledPriorDay);
	}

	private Streak createInitialStreak(Streak streak) {
		return streakRepository.save(streak);
	}

	@Override
	@Transactional(readOnly = true)
	public ListStreakDto findAll(UUID achieverId, LocalDate startDate, LocalDate endDate) {
		List<StreakDto> dailyStreaks = findStreaksByType(achieverId, StreakType.DAILY, startDate, endDate);
		List<StreakDto> weeklyStreaks = findStreaksByType(achieverId, StreakType.WEEKLY, startDate, endDate);
		List<StreakDto> monthlyStreaks = findStreaksByType(achieverId, StreakType.MONTHLY, startDate, endDate);
		return ListStreakDto.of(dailyStreaks, weeklyStreaks, monthlyStreaks);
	}

	@NotNull
	private List<StreakDto> findStreaksByType(UUID achieverId, StreakType streakType, LocalDate startDate, LocalDate endDate) {
		return streakRepository.findByAchieverIdAndStreakTypeAndFilledDateBetween(achieverId, streakType, startDate, endDate).stream()
			.map(StreakDto::from)
			.toList();
	}
}
