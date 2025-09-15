package com.gomo.app.interest.application;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.image.ImageService;
import com.gomo.app.interest.application.translator.RegistrantTranslator;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.Logo;
import com.gomo.app.interest.domain.model.Registrant;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.presentation.request.CreateInterestRequest;
import com.gomo.app.interest.presentation.response.CreateInterestResponse;
import com.gomo.app.logging.AuditLog;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class CreateInterestUseCase {

	private final MemberService memberService;
	private final ImageService imageService;
	private final InterestRepository interestRepository;

	@AuditLog(action = "CREATE_INTEREST")
	public CreateInterestResponse create(UUID registrantId, CreateInterestRequest request) {
		ensureNotExceedInterestQuota(registrantId);
		String logoUrl = imageService.uploadImage(request.getLogo());
		Interest interest = request.toDomain(InterestId.of(UUIDGenerator.generate()), RegistrantId.of(registrantId), Logo.of(logoUrl));
		Interest savedInterest = interestRepository.save(interest);
		return CreateInterestResponse.of(savedInterest.getId());
	}

	private void ensureNotExceedInterestQuota(UUID registrantId) {
		Registrant registrant = RegistrantTranslator.from(memberService.find(MemberId.of(registrantId)));
		long interestCount = interestRepository.countAllByRegistrantId(registrant.getId());
		registrant.validateInterestQuota(interestCount);
	}
}
