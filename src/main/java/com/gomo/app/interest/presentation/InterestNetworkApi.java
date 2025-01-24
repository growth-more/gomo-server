package com.gomo.app.interest.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.interest.application.CreateInterestRelationUseCase;
import com.gomo.app.interest.application.DeleteInterestRelationUseCase;
import com.gomo.app.interest.application.ReadInterestNetworkUseCase;
import com.gomo.app.interest.domain.model.InterestRelationId;
import com.gomo.app.interest.presentation.request.CreateInterestRelationRequest;
import com.gomo.app.interest.presentation.response.CreateInterestRelationResponse;
import com.gomo.app.interest.presentation.response.InterestNetworkResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/interests/networks")
@Presentation
public class InterestNetworkApi {

	private final ReadInterestNetworkUseCase readInterestNetworkUseCase;
	private final CreateInterestRelationUseCase createInterestRelationUseCase;
	private final DeleteInterestRelationUseCase deleteInterestRelationUseCase;

	@GetMapping
	public ResponseEntity<InterestNetworkResponse> find() {
		return null;
	}

	@PostMapping("/relations")
	public ResponseEntity<CreateInterestRelationResponse> createRelation(@RequestBody CreateInterestRelationRequest request) {
		return null;
	}

	@DeleteMapping("/relations/{id}")
	public ResponseEntity<Void> deleteRelation(@PathVariable("id") InterestRelationId interestRelationId) {
		return null;
	}
}
