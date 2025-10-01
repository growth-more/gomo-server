package com.gomo.app.core.streak.application.usecase;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.streak.application.port.CreateAchieverPortIn;
import com.gomo.app.core.streak.domain.model.Achiever;
import com.gomo.app.core.streak.domain.model.AchieverId;
import com.gomo.app.core.streak.domain.repository.AchieverRepository;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class CreateAchieverUseCase implements CreateAchieverPortIn {

	private final AchieverRepository achieverRepository;

	@AuditLog(action = "CREATE_STREAK")
	@Override
	public UUID create(UUID achieverId) {
		Achiever savedAchiever = achieverRepository.save(Achiever.of(AchieverId.of(achieverId)));
		return savedAchiever.id();
	}
}
