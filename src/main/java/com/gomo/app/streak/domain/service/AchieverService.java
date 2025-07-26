package com.gomo.app.streak.domain.service;

import java.util.UUID;

import com.gomo.app.common.DomainService;
import com.gomo.app.streak.domain.model.Achiever;
import com.gomo.app.streak.domain.model.AchieverId;
import com.gomo.app.streak.domain.repository.AchieverRepository;
import com.gomo.app.streak.exception.AchieverErrorCode;
import com.gomo.app.streak.exception.AchieverNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class AchieverService {

	private final AchieverRepository achieverRepository;

	public Achiever create(UUID achieverId) {
		return achieverRepository.save(Achiever.of(AchieverId.of(achieverId)));
	}

	public Achiever find(AchieverId achieverId) {
		return achieverRepository.findById(achieverId).orElseThrow(() -> new AchieverNotFoundException(AchieverErrorCode.NOT_FOUND));
	}
}
