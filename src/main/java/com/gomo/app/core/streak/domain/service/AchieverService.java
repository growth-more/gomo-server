package com.gomo.app.core.streak.domain.service;

import com.gomo.app.common.arch.DomainService;
import com.gomo.app.core.streak.domain.model.Achiever;
import com.gomo.app.core.streak.domain.model.AchieverId;
import com.gomo.app.core.streak.domain.repository.AchieverRepository;
import com.gomo.app.core.streak.exception.AchieverErrorCode;
import com.gomo.app.core.streak.exception.AchieverNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class AchieverService {

	private final AchieverRepository achieverRepository;

	public Achiever find(AchieverId achieverId) {
		return achieverRepository.findById(achieverId).orElseThrow(() -> new AchieverNotFoundException(AchieverErrorCode.NOT_FOUND));
	}
}
