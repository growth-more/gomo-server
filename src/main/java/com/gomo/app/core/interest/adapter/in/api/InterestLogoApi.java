package com.gomo.app.core.interest.adapter.in.api;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.interest.application.port.in.UpdateLogoUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/interests/{id}/logos")
@CoreApi
public class InterestLogoApi {

	private final UpdateLogoUseCase updateLogoUseCase;

	@PutMapping
	public ResponseEntity<Void> update(@PathVariable("id") UUID interestId, @RequestPart MultipartFile updatedLogo) {
		updateLogoUseCase.update(interestId, updatedLogo);
		return ResponseEntity.noContent().build();
	}
}
