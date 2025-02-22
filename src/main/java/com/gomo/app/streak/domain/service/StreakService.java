package com.gomo.app.streak.domain.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.domain.service.DomainService;
import com.gomo.app.streak.domain.model.AchieverId;
import com.gomo.app.streak.domain.model.Streak;
import com.gomo.app.streak.domain.model.StreakType;
import com.gomo.app.streak.domain.repository.StreakRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@DomainService
public class StreakService {

	private final StreakRepository streakRepository;

	@Transactional
	public Streak fill(Streak streak) {
		return streakRepository.findByAchieverIdAndStreakTypeAndFilledDate(streak.getAchieverId(), streak.getStreakType(), streak.getFilledDate())
			.map(existingStreak -> {
				existingStreak.increaseCompletedQuestCount();
				return existingStreak;
			})
			.orElseGet(() -> createInitialStreak(streak));
	}

	public List<Streak> findAllByStreakType(AchieverId achieverId, StreakType streakType, LocalDate startDate, LocalDate endDate) {
		return streakRepository.findByAchieverIdAndStreakTypeAndFilledDateBetween(achieverId, streakType, startDate, endDate);
	}

	private Streak createInitialStreak(Streak streak) {
		return streakRepository.save(streak);
	}
}
