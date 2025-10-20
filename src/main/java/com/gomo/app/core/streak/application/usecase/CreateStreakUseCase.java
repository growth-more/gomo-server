package com.gomo.app.core.streak.application.usecase;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.streak.application.port.CreateStreakPortIn;
import com.gomo.app.core.streak.domain.model.Streak;
import com.gomo.app.core.streak.domain.model.StreakType;
import com.gomo.app.core.streak.domain.service.StreakService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class CreateStreakUseCase implements CreateStreakPortIn {

	private final StreakService streakService;

	@Override
	public void create(UUID achieverId, String streakType, LocalDate creationDate) {
		Streak streak = Streak.of(UUIDGenerator.generate(), achieverId, StreakType.valueOf(streakType), creationDate, 1);
		streakService.fill(streak);
	}
}
