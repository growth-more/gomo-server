package com.gomo.app.interest.presentation;

import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.interest.application.UpdateInterestLogoUseCase;
import com.gomo.app.interest.domain.model.InterestId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/interests/{id}/logos")
@Presentation
public class InterestLogoApi {

	private final UpdateInterestLogoUseCase updateInterestLogoUseCase;

	@PutMapping
	public ResponseEntity<Void> update(@PathVariable("id") UUID interestId, @RequestPart MultipartFile updatedLogo) {
		updateInterestLogoUseCase.update(InterestId.of(interestId), updatedLogo);
		return ResponseEntity.noContent().build();
	}
}
