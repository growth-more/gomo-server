package com.gomo.app.core.interest.domain.service;

import java.util.HashSet;
import java.util.Set;

import com.gomo.app.common.arch.DomainService;
import com.gomo.app.core.interest.domain.repository.InterestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class LogoService {

	private final InterestRepository interestRepository;

	public Set<String> findAllUrls() {
		return new HashSet<>(interestRepository.findAllLogoUrl());
	}
}
