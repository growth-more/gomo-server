package com.gomo.app.interest.application;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.interest.application.port.command.UpdateInterestCommand;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.InterestName;
import com.gomo.app.interest.domain.service.InterestService;
import com.gomo.app.logging.AuditLog;

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
