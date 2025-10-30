package com.gomo.app.core.interest.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.interest.application.port.in.DeleteInterestUseCase;
import com.gomo.app.core.interest.application.port.out.LogoDeleter;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.core.interest.domain.service.InterestRelationService;
import com.gomo.app.core.interest.domain.service.InterestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class DeleteInterestService implements DeleteInterestUseCase {

	private final LogoDeleter logoDeleter;
	private final MajorInterestRepository majorInterestRepository;
	private final InterestRelationService interestRelationService;
	private final InterestService interestService;
	private final InterestRepository interestRepository;

	/*
		TODO <jhl221123>: 도메인 정책과 서비스 결합도에 관한 리팩터링
		관심사와 로고, 주요관심사, 관계선을 함께 제거하는 것은 도메인 정책입니다.
		하지만 관련 로직을 도메인 서비스가 처리하면 도메인 서비스의 외부 의존성이 높아집니다.
		지금처럼 유스케이스에서 트랜잭션으로 처리하면 설계상 일관성은 유지되지만, 도메인 모델이 빈약해집니다.
		따라서 유스케이스는 관심사 삭제만 수행 -> 관심사 도메인 엔티티 내 이벤트 발행 -> 서로 다른 모듈에서 소비
		동시에 Zipkin, Jaeger 등의 도구를 활용해 분산 환경에서 이벤트 트래킹을 준비해야합니다.
	 */
	@AuditLog(action = "DELETE_INTEREST")
	public void delete(UUID registrantId, UUID interestId) {
		Interest interest = interestService.find(interestId);
		interest.validateAuthority(registrantId);

		deleteLogoUrl(interest);
		deleteMajorInterest(interestId);
		deleteInterestRelations(interestId);
		interestRepository.delete(interest);
	}

	private void deleteLogoUrl(Interest interest) {
		if (!interest.hasDefaultLogo()) {
			logoDeleter.delete(interest.getLogo().getUrl());
		}
	}

	private void deleteMajorInterest(UUID interestId) {
		majorInterestRepository.deleteByInterestId(interestId);
	}

	private void deleteInterestRelations(UUID interestId) {
		List<InterestRelation> interestRelations = interestRelationService.findAllByInterestId(interestId);
		if (!interestRelations.isEmpty()) {
			for (InterestRelation relation : interestRelations) {
				Interest parentInterest = interestService.find(relation.getParentInterestId());
				Interest childInterest = interestService.find(relation.getChildInterestId());
				interestRelationService.delete(relation, parentInterest, childInterest);
			}
		}
	}
}
