package com.gomo.app.interest.application;

import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.presentation.request.CreateInterestRequest;
import com.gomo.app.interest.presentation.response.CreateInterestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateInterestUseCase {

    private final InterestRepository interestRepository;

    // TODO <jhl221123>: 이미지 저장 기능 구현 후, 실제 url을 전달하도록 수정이 필요하다.
    public CreateInterestResponse create(RegistrantId registrantId, CreateInterestRequest request, MultipartFile logo) {
        InterestId interestId = InterestId.of(UUIDGenerator.generate());
        Interest interest = request.toDomain(interestId, registrantId, null);
        Interest savedInterest = interestRepository.save(interest);
        return CreateInterestResponse.of(savedInterest.getId());
    }
}
