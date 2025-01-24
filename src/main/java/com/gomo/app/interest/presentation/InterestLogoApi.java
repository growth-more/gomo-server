package com.gomo.app.interest.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.interest.application.UpdateInterestUseCase;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.presentation.request.LogoUpdateInterestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/interests/{id}/logos")
@Presentation
public class InterestLogoApi {

	private final UpdateInterestUseCase updateInterestUseCase;

	@PutMapping
	public ResponseEntity<Void> update(@PathVariable("id") InterestId interestId, @RequestBody LogoUpdateInterestRequest request) {
		return null;
	}
}
