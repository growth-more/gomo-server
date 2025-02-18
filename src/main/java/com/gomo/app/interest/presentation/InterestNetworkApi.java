package com.gomo.app.interest.presentation;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.interest.application.CreateInterestRelationUseCase;
import com.gomo.app.interest.application.DeleteInterestRelationUseCase;
import com.gomo.app.interest.application.ReadInterestNetworkUseCase;
import com.gomo.app.interest.domain.model.InterestRelationId;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.presentation.request.CreateInterestRelationRequest;
import com.gomo.app.interest.presentation.response.CreateInterestRelationResponse;
import com.gomo.app.interest.presentation.response.InterestNetworkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/interests/networks")
@Presentation
public class InterestNetworkApi {

	private final CreateInterestRelationUseCase createInterestRelationUseCase;
	private final ReadInterestNetworkUseCase readInterestNetworkUseCase;
	private final DeleteInterestRelationUseCase deleteInterestRelationUseCase;

	@PostMapping("/relations")
	public ResponseEntity<CreateInterestRelationResponse> createRelation(@Auth AuthInfo authInfo, @RequestBody CreateInterestRelationRequest request) {
		CreateInterestRelationResponse response = createInterestRelationUseCase.create(RegistrantId.of(authInfo.getMemberId()), request);
		return ResponseEntity.status(CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<InterestNetworkResponse> find(@Auth AuthInfo authInfo) {
		InterestNetworkResponse response = readInterestNetworkUseCase.find(authInfo.getMemberId());
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/relations/{id}")
	public ResponseEntity<Void> deleteRelation(@Auth AuthInfo authInfo, @PathVariable("id") UUID interestRelationId) {
		deleteInterestRelationUseCase.delete(authInfo.getMemberId(), InterestRelationId.of(interestRelationId));
		return ResponseEntity.noContent().build();
	}
}
