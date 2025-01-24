package com.gomo.app.interest.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.presentation.request.CreateInterestRequest;
import com.gomo.app.interest.presentation.response.CreateInterestResponse;
import com.gomo.app.member.domain.model.MemberId;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateInterestUseCase {

    private final InterestRepository interestRepository;

    public CreateInterestResponse create(MemberId memberId, CreateInterestRequest request) {
        return null;
    }
}
