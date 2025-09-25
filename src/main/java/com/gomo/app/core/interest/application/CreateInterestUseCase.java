package com.gomo.app.core.interest.application;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.interest.application.port.ReadRegistrantPortOut;
import com.gomo.app.core.interest.application.port.UploadLogoPortOut;
import com.gomo.app.core.interest.application.port.command.CreateInterestCommand;
import com.gomo.app.core.interest.application.port.dto.CreateInterestDto;
import com.gomo.app.core.interest.application.port.dto.LogoDto;
import com.gomo.app.core.interest.application.port.dto.RegistrantDto;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestId;
import com.gomo.app.core.interest.domain.model.InterestName;
import com.gomo.app.core.interest.domain.model.Logo;
import com.gomo.app.core.interest.domain.model.Registrant;
import com.gomo.app.core.interest.domain.model.RegistrantId;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class CreateInterestUseCase {

	private final ReadRegistrantPortOut readRegistrantPortOut;
	private final UploadLogoPortOut uploadLogoPortOut;
	private final InterestRepository interestRepository;

	@AuditLog(action = "CREATE_INTEREST")
	public CreateInterestDto create(CreateInterestCommand command) {
		ensureNotExceedInterestQuota(command.registrantId());
		LogoDto logoDto = uploadLogoPortOut.upload(command.logoFile());
		Interest interest = Interest.of(
			InterestId.of(UUIDGenerator.generate()),
			RegistrantId.of(command.registrantId()),
			InterestName.of(command.name()),
			Logo.of(logoDto.url()),
			command.colorCode()
		);
		Interest savedInterest = interestRepository.save(interest);
		return CreateInterestDto.of(savedInterest.uuid());
	}

	private void ensureNotExceedInterestQuota(UUID registrantId) {
		RegistrantDto dto = readRegistrantPortOut.find(registrantId);
		Registrant registrant = Registrant.of(RegistrantId.of(dto.id()), dto.subscriptionPlan());
		long interestCount = interestRepository.countAllByRegistrantId(registrant.getId());
		registrant.validateInterestQuota(interestCount);
	}
}
