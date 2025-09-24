package com.gomo.app.streak.application;

import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.streak.domain.model.Achiever;
import com.gomo.app.streak.domain.model.AchieverId;
import com.gomo.app.streak.domain.service.AchieverService;
import com.gomo.app.streak.presentation.response.ReadAchieverResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadAchieverUseCase {

	private final AchieverService achieverService;

	public ReadAchieverResponse find(UUID achieverId) {
		Achiever achiever = achieverService.find(AchieverId.of(achieverId));
		return ReadAchieverResponse.of(achiever);
	}
}
