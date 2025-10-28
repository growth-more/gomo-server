package com.gomo.app.core.streak.application.usecase;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.streak.application.port.CreateAchieverPortIn;
import com.gomo.app.core.streak.domain.model.Achiever;
import com.gomo.app.core.streak.domain.repository.AchieverRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class CreateAchieverUseCase implements CreateAchieverPortIn {

	private final AchieverRepository achieverRepository;

	// TODO [2025-10-10] jhl221123 : 이미 해당 id로 존재한다면 예외가 발생해야 합니다.
	@AuditLog(action = "CREATE_ACHIEVER")
	@Override
	public UUID create(UUID achieverId) {
		Achiever savedAchiever = achieverRepository.save(Achiever.of(achieverId));
		return savedAchiever.getId();
	}
}
