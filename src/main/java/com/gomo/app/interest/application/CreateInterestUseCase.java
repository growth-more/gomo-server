package com.gomo.app.interest.application;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.image.ImageService;
import com.gomo.app.interest.application.translator.InterestQuotaTranslator;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.InterestQuota;
import com.gomo.app.interest.domain.model.Logo;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.presentation.request.CreateInterestRequest;
import com.gomo.app.interest.presentation.response.CreateInterestResponse;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class CreateInterestUseCase {

    private final InterestRepository interestRepository;
    private final MemberService memberService;
    private final ImageService imageService;

    public CreateInterestResponse create(UUID registrantId, CreateInterestRequest request) {
        String logoUrl = imageService.uploadImage(request.getLogo());
        Interest interest = request.toDomain(InterestId.of(UUIDGenerator.generate()), RegistrantId.of(registrantId), Logo.of(logoUrl));
        ensureNotExceedInterestQuota(registrantId, interest);
        Interest savedInterest = interestRepository.save(interest);
        return CreateInterestResponse.of(savedInterest.getId());
    }

    private void ensureNotExceedInterestQuota(UUID registrantId, Interest interest) {
        InterestQuota interestQuota = getInterestQuota(registrantId);
        long interestCount = interestRepository.countAllByRegistrantId(interest.getRegistrantId());
        interestQuota.validateCount(interestCount);
    }

    private InterestQuota getInterestQuota(UUID registrantId) {
        // TODO <jhl221123>: Registrant 객채를 도입해 컨텍스트 간 결합도 완화가 필요합니다.
        Member member = memberService.find(MemberId.of(registrantId));
		return InterestQuotaTranslator.from(member.getSubscriptionPlan());
    }
}
