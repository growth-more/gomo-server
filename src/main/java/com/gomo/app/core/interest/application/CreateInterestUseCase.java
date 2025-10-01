package com.gomo.app.core.interest.application;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.interest.application.port.ReadRegistrantPortOut;
import com.gomo.app.core.interest.application.port.command.CreateInterestCommand;
import com.gomo.app.core.interest.application.port.dto.RegistrantDto;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestId;
import com.gomo.app.core.interest.domain.model.InterestName;
import com.gomo.app.core.interest.domain.model.Logo;
import com.gomo.app.core.interest.domain.model.Registrant;
import com.gomo.app.core.interest.domain.model.RegistrantId;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.support.image.port.UploadImagePortIn;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class CreateInterestUseCase {

	private final ReadRegistrantPortOut readRegistrantPortOut;
	private final UploadImagePortIn uploadImagePortIn;
	private final InterestRepository interestRepository;

	@AuditLog(action = "CREATE_INTEREST")
	public UUID create(CreateInterestCommand command) {
		ensureNotExceedInterestQuota(command.registrantId());
		String logoUrl = uploadImagePortIn.upload(command.logoFile()).orElse(null);
		Interest interest = Interest.of(
			InterestId.of(UUIDGenerator.generate()),
			RegistrantId.of(command.registrantId()),
			InterestName.of(command.name()),
			Logo.of(logoUrl),
			command.colorCode()
		);
		Interest savedInterest = interestRepository.save(interest);
		return savedInterest.id();
	}

	private void ensureNotExceedInterestQuota(UUID registrantId) {
		RegistrantDto dto = readRegistrantPortOut.find(registrantId);
		Registrant registrant = Registrant.of(RegistrantId.of(dto.id()), dto.subscriptionPlan());
		long interestCount = interestRepository.countAllByRegistrantId(registrant.getId());
		registrant.validateInterestQuota(interestCount);
	}
}
