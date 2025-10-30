package com.gomo.app.core.interest.application.service;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.interest.application.port.command.CreateInterestCommand;
import com.gomo.app.core.interest.application.port.dto.RegistrantDto;
import com.gomo.app.core.interest.application.port.in.CreateInterestUseCase;
import com.gomo.app.core.interest.application.port.out.LogoUploader;
import com.gomo.app.core.interest.application.port.out.RegistrantReader;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestName;
import com.gomo.app.core.interest.domain.model.Logo;
import com.gomo.app.core.interest.domain.model.Registrant;
import com.gomo.app.core.interest.domain.repository.InterestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class CreateInterestService implements CreateInterestUseCase {

	private final RegistrantReader registrantReader;
	private final LogoUploader logoUploader;
	private final InterestRepository interestRepository;

	@AuditLog(action = "CREATE_INTEREST")
	public UUID create(CreateInterestCommand command) {
		ensureNotExceedInterestQuota(command.registrantId());
		String logoUrl = logoUploader.upload(command.logoFile()).orElse(null);
		Interest interest = Interest.of(
			UUIDGenerator.generate(),
			command.registrantId(),
			InterestName.of(command.name()),
			Logo.of(logoUrl),
			command.colorCode()
		);
		Interest savedInterest = interestRepository.save(interest);
		return savedInterest.getId();
	}

	private void ensureNotExceedInterestQuota(UUID registrantId) {
		RegistrantDto dto = registrantReader.find(registrantId);
		Registrant registrant = Registrant.of(dto.id(), dto.subscriptionPlan());
		long interestCount = interestRepository.countAllByRegistrantId(registrant.getId());
		registrant.validateInterestQuota(interestCount);
	}
}
