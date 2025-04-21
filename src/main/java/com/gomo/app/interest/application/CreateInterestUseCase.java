package com.gomo.app.interest.application;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.domain.service.ImageService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.interest.application.translator.InterestQuotaTranslator;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.InterestQuota;
import com.gomo.app.interest.domain.model.Logo;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.service.InterestService;
import com.gomo.app.interest.presentation.request.CreateInterestRequest;
import com.gomo.app.interest.presentation.response.CreateInterestResponse;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.service.ReadMemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class CreateInterestUseCase {

    private final ReadMemberService readMemberService;
    private final ImageService imageService;
    private final InterestService interestService;

    public CreateInterestResponse create(RegistrantId registrantId, CreateInterestRequest request) {
        Interest interest = getInterest(registrantId, request);
        InterestQuota interestQuota = getInterestQuota(registrantId);
        Interest savedInterest = interestService.create(interest, interestQuota);
        return CreateInterestResponse.of(savedInterest.getId());
    }

    private Interest getInterest(RegistrantId registrantId, CreateInterestRequest request) {
        String logoUrl = imageService.uploadImage(request.getLogo());
		return request.toDomain(InterestId.of(UUIDGenerator.generate()), registrantId, Logo.of(logoUrl));
    }

    private InterestQuota getInterestQuota(RegistrantId registrantId) {
        Member member = readMemberService.find(registrantId.getId());
		return InterestQuotaTranslator.from(member.getSubscriptionPlan());
    }
}

