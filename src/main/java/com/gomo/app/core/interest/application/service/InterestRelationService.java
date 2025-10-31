package com.gomo.app.core.interest.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.interest.application.port.in.InterestRelationCreator;
import com.gomo.app.core.interest.application.port.in.InterestRelationDeleter;
import com.gomo.app.core.interest.domain.exception.InterestRelationDuplicatedException;
import com.gomo.app.core.interest.domain.exception.InterestRelationNotFoundException;
import com.gomo.app.core.interest.domain.exception.code.InterestRelationErrorCode;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.core.interest.domain.service.InterestNetworkBuilder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class InterestRelationService implements InterestRelationCreator, InterestRelationDeleter {

	private final InterestService interestService;
	private final ProficiencyService proficiencyService;
	private final InterestRelationRepository interestRelationRepository;
	private final InterestNetworkBuilder interestNetworkBuilder;

	@AuditLog(action = "CREATE_INTEREST_RELATION")
	public UUID create(UUID registrantId, UUID parentInterestId, UUID childInterestId) {
		ensureNotDuplicated(parentInterestId, childInterestId);
		InterestRelation interestRelation = InterestRelation.of(
			UUIDGenerator.generate(),
			registrantId,
			parentInterestId,
			childInterestId
		);

		List<InterestRelation> relations = interestRelationRepository.findAllByRegistrantId(registrantId);
		relations.add(interestRelation);
		interestNetworkBuilder.validateAcyclic(relations, parentInterestId);

		Interest childInterest = interestService.readById(childInterestId);
		int deltaScore = childInterest.getProficiency().getTotalScore();
		proficiencyService.propagate(parentInterestId, deltaScore);
		return interestRelationRepository.save(interestRelation).getId();
	}

	InterestRelation readById(UUID interestRelationId) {
		return interestRelationRepository.findById(interestRelationId)
			.orElseThrow(() -> new InterestRelationNotFoundException(InterestRelationErrorCode.NOT_FOUND));
	}

	List<InterestRelation> readAllByInterestId(UUID interestId) {
		return interestRelationRepository.findAllByInterestId(interestId);
	}

	List<InterestRelation> readAllByRegistrantId(UUID registrantId) {
		return interestRelationRepository.findAllByRegistrantId(registrantId);
	}

	@AuditLog(action = "DELETE_INTEREST_RELATION")
	public void delete(UUID registrantId, UUID interestRelationId) {
		InterestRelation interestRelation = readById(interestRelationId);
		interestRelation.validateAuthority(registrantId);

		Interest childInterest = interestService.readById(interestRelation.getChildInterestId());
		int deltaScore = -1 * childInterest.getProficiency().getTotalScore();
		proficiencyService.propagate(interestRelation.getParentInterestId(), deltaScore);
		interestRelationRepository.delete(interestRelation);
	}

	private void ensureNotDuplicated(UUID parentInterestId, UUID childInterestId) {
		if (interestRelationRepository.existsRelationFor(parentInterestId, childInterestId)) {
			throw new InterestRelationDuplicatedException(InterestRelationErrorCode.DUPLICATED);
		}
	}
}
