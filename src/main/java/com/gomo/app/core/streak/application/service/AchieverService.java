package com.gomo.app.core.streak.application.service;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.streak.application.port.dto.AchieverDto;
import com.gomo.app.core.streak.application.port.in.AchieverCreator;
import com.gomo.app.core.streak.application.port.in.AchieverReader;
import com.gomo.app.core.streak.domain.exception.AchieverErrorCode;
import com.gomo.app.core.streak.domain.exception.AchieverNotFoundException;
import com.gomo.app.core.streak.domain.model.Achiever;
import com.gomo.app.core.streak.domain.repository.AchieverRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class AchieverService implements AchieverCreator, AchieverReader {

	private final AchieverRepository achieverRepository;

	@Override
	@AuditLog(action = "ACHIEVER_CREATE")
	public UUID create(UUID achieverId) {
		ensureNotDuplicated(achieverId);
		Achiever savedAchiever = achieverRepository.save(Achiever.of(achieverId));
		return savedAchiever.getId();
	}

	private void ensureNotDuplicated(UUID achieverId) {
		if (achieverRepository.existsById(achieverId)) {
			throw new IllegalStateException("already exists achiever with id: " + achieverId);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public AchieverDto read(UUID achieverId) {
		return AchieverDto.from(findById(achieverId));
	}

	Achiever findById(UUID achieverId) {
		return achieverRepository.findById(achieverId).orElseThrow(() -> new AchieverNotFoundException(AchieverErrorCode.NOT_FOUND));
	}
}
