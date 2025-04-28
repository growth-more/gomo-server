package com.gomo.app.interest.application;

import java.util.List;
import java.util.stream.IntStream;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.common.util.JsonParser;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.interest.domain.service.InterestService;
import com.gomo.app.interest.presentation.response.ListInterestResponse;
import com.gomo.app.interest.presentation.response.ReadInterestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadInterestUseCase {

	private final InterestService interestService;
	private final InterestRepository interestRepository;
	private final MajorInterestRepository majorInterestRepository;

	public ReadInterestResponse find(InterestId interestId) {
		Interest interest = interestService.find(interestId);
		return createResponse(interest);
	}

	public ListInterestResponse findAll(RegistrantId registrantId) {
		List<ReadInterestResponse> interestResponses = interestRepository.findAllByRegistrantId(registrantId)
			.stream().map(ReadInterestResponse::of)
			.toList();
		markMajorInterests(interestResponses);

		return ListInterestResponse.of(interestResponses);
	}

	@NotNull
	private ReadInterestResponse createResponse(Interest interest) {
		ReadInterestResponse response = ReadInterestResponse.of(interest);
		if(majorInterestRepository.existsMajorInterestByInterestId(interest.getId())) {
			response.updateMajorInterest();
		}

		return response;
	}

	private void markMajorInterests(List<ReadInterestResponse> interests) {
		String interestIdsJson = JsonParser.toJson(interests.stream().map(ReadInterestResponse::getId).toList());
		List<Long> isMajorInterests = majorInterestRepository.existsAsMajorInterests(interestIdsJson);
		IntStream.range(0, interests.size())
			.filter(i -> isMajorInterests.get(i) == 1)
			.forEach(i -> interests.get(i).updateMajorInterest());
	}
}
