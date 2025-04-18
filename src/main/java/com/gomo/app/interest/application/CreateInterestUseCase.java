package com.gomo.app.interest.application;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.domain.service.ImageService;
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
@Transactional
public class CreateInterestUseCase {

    private final ImageService imageService;
    private final InterestRepository interestRepository;

    public CreateInterestResponse create(RegistrantId registrantId, CreateInterestRequest request, MultipartFile logo) {
        InterestId interestId = InterestId.of(UUIDGenerator.generate());
        String logoImageName = imageService.uploadImage(logo);

        Interest interest = request.toDomain(interestId, registrantId, logoImageName);
        Interest savedInterest = interestRepository.save(interest);
        return CreateInterestResponse.of(savedInterest.getId());
    }
}

