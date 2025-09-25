package com.gomo.app.core.interest.presentation;

import static org.springframework.http.HttpStatus.*;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.Presentation;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;
import com.gomo.app.core.interest.application.CreateInterestRelationUseCase;
import com.gomo.app.core.interest.application.DeleteInterestRelationUseCase;
import com.gomo.app.core.interest.application.ReadInterestNetworkUseCase;
import com.gomo.app.core.interest.application.port.dto.CreateInterestRelationDto;
import com.gomo.app.core.interest.application.port.dto.InterestNetworkDto;
import com.gomo.app.core.interest.presentation.request.CreateInterestRelationRequest;
import com.gomo.app.core.interest.presentation.response.CreateInterestRelationResponse;
import com.gomo.app.core.interest.presentation.response.InterestNetworkResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/interests/networks")
@Presentation
public class InterestNetworkApi {

	private final CreateInterestRelationUseCase createInterestRelationUseCase;
	private final ReadInterestNetworkUseCase readInterestNetworkUseCase;
	private final DeleteInterestRelationUseCase deleteInterestRelationUseCase;

	@PostMapping("/relations")
	public ResponseEntity<CreateInterestRelationResponse> createRelation(@Auth AuthInfo authInfo, @RequestBody CreateInterestRelationRequest request) {
		CreateInterestRelationDto dto = createInterestRelationUseCase.create(authInfo.getMemberId(), request.getParentInterestId(), request.getChildInterestId());
		return ResponseEntity.status(CREATED).body(CreateInterestRelationResponse.of(dto.id()));
	}

	@GetMapping
	public ResponseEntity<InterestNetworkResponse> find(@Auth AuthInfo authInfo) {
		InterestNetworkDto interestNetworkDto = readInterestNetworkUseCase.find(authInfo.getMemberId());
		return ResponseEntity.ok(InterestNetworkResponse.from(interestNetworkDto));
	}

	@DeleteMapping("/relations/{id}")
	public ResponseEntity<Void> deleteRelation(@Auth AuthInfo authInfo, @PathVariable("id") UUID interestRelationId) {
		deleteInterestRelationUseCase.delete(authInfo.getMemberId(), interestRelationId);
		return ResponseEntity.noContent().build();
	}
}
