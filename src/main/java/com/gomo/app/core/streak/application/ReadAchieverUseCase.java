package com.gomo.app.core.streak.application;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.streak.application.port.dto.AchieverDto;
import com.gomo.app.core.streak.domain.model.AchieverId;
import com.gomo.app.core.streak.domain.service.AchieverService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadAchieverUseCase {

	private final AchieverService achieverService;

	public AchieverDto find(UUID achieverId) {
		return AchieverDto.from(achieverService.find(AchieverId.of(achieverId)));
	}
}
