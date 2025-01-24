package com.gomo.app.interest.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.interest.domain.model.MajorInterestId;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class DeleteMajorInterestUseCase {

	private final MajorInterestRepository majorInterestRepository;

	public void delete(MajorInterestId majorInterestId) {

	}
}
