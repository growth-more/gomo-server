package com.gomo.app.core.interest.application.usecase;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.interest.application.port.command.UpdateInterestCommand;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestId;
import com.gomo.app.core.interest.domain.model.InterestName;
import com.gomo.app.core.interest.domain.service.InterestService;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateInterestUseCase {

	private final InterestService interestService;

	@AuditLog(action = "UPDATE_INTEREST")
	public void update(UpdateInterestCommand command) {
		Interest interest = interestService.find(InterestId.of(command.interestId()));
		interest.validateAuthority(command.registrantId());
		interest.updateName(InterestName.of(command.name()));
		interest.updateColorCode(command.colorCode());
	}
}
