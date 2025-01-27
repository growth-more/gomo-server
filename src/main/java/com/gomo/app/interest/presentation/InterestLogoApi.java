package com.gomo.app.interest.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.interest.application.UpdateInterestLogoUseCase;
import com.gomo.app.interest.domain.model.InterestId;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/interests/{id}/logos")
@Presentation
public class InterestLogoApi {

	private final UpdateInterestLogoUseCase updateInterestLogoUseCase;

	@PutMapping
	public ResponseEntity<Void> update(@PathVariable("id") InterestId interestId, @RequestPart MultipartFile updatedLogo) {
		return null;
	}
}
