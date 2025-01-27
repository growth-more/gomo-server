package com.gomo.app.streak.domain.service;

import java.util.List;

import com.gomo.app.common.domain.service.DomainService;
import com.gomo.app.streak.domain.model.AchieverId;
import com.gomo.app.streak.domain.model.Streak;
import com.gomo.app.streak.domain.model.StreakId;
import com.gomo.app.streak.domain.model.StreakType;
import com.gomo.app.streak.domain.repository.StreakRepository;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class StreakService {

	private final StreakRepository streakRepository;

	public StreakId create(Streak streak) {
		return null;
	}
	public List<Streak> findAllByStreakType(AchieverId achieverId, StreakType streakType) {
		return null;
	}
}
