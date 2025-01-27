package com.gomo.app.interest.application;

import static com.gomo.app.common.exception.DomainErrorCode.*;

import java.util.List;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.exception.NotFoundException;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.presentation.response.ListInterestResponse;
import com.gomo.app.interest.presentation.response.ReadInterestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadInterestUseCase {

	private final InterestRepository interestRepository;

	public ReadInterestResponse find(InterestId interestId) {
		Interest interest = interestRepository.findById(interestId)
			.orElseThrow(() -> new NotFoundException(NOT_FOUND, "Interest not found with id: " + interestId.getId()));

		return ReadInterestResponse.of(interest);
	}

	public ListInterestResponse findAll(RegistrantId registrantId) {
		List<ReadInterestResponse> list = interestRepository.findAllByRegistrantId(registrantId)
			.stream().map(ReadInterestResponse::of).toList();

		return ListInterestResponse.of(list);
	}
}
