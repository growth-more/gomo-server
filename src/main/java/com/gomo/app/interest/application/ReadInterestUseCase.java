package com.gomo.app.interest.application;

import static com.gomo.app.common.exception.DomainErrorCode.*;

import java.util.List;
import java.util.stream.IntStream;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.exception.NotFoundException;
import com.gomo.app.common.util.JsonParser;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.interest.presentation.response.ListInterestResponse;
import com.gomo.app.interest.presentation.response.ReadInterestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadInterestUseCase {

	private final InterestRepository interestRepository;
	private final MajorInterestRepository majorInterestRepository;

	public ReadInterestResponse find(InterestId interestId) {
		Interest interest = interestRepository.findById(interestId)
			.orElseThrow(() -> new NotFoundException(NOT_FOUND, "Interest not found with id: " + interestId.getId()));

		ReadInterestResponse response = ReadInterestResponse.of(interest);
		if(majorInterestRepository.existsMajorInterestByInterestId(interestId)) {
			response.updateMajorInterest();
		}

		return response;
	}

	public ListInterestResponse findAll(RegistrantId registrantId) {
		List<ReadInterestResponse> interestResponses = interestRepository.findAllByRegistrantId(registrantId)
			.stream().map(ReadInterestResponse::of).toList();
		markMajorInterests(interestResponses);

		return ListInterestResponse.of(interestResponses);
	}

	private void markMajorInterests(List<ReadInterestResponse> interests) {
		String interestIdsJson = JsonParser.toJson(interests.stream().map(ReadInterestResponse::getId).toList());
		List<Long> isMajorInterests = majorInterestRepository.existsAsMajorInterests(interestIdsJson);
		IntStream.range(0, interests.size())
			.filter(i -> isMajorInterests.get(i) == 1)
			.forEach(i -> interests.get(i).updateMajorInterest());
	}
}
