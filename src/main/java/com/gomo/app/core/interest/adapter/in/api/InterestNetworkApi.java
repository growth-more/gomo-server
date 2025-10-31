package com.gomo.app.core.interest.adapter.in.api;

import static org.springframework.http.HttpStatus.*;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.interest.adapter.in.api.request.CreateInterestRelationRequest;
import com.gomo.app.core.interest.adapter.in.api.response.CreateInterestRelationResponse;
import com.gomo.app.core.interest.adapter.in.api.response.InterestNetworkResponse;
import com.gomo.app.core.interest.application.port.dto.InterestNetworkDto;
import com.gomo.app.core.interest.application.port.in.InterestNetworkReader;
import com.gomo.app.core.interest.application.port.in.InterestRelationCreator;
import com.gomo.app.core.interest.application.port.in.InterestRelationDeleter;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/interests/networks")
@CoreApi
public class InterestNetworkApi {

	private final InterestRelationCreator interestRelationCreator;
	private final InterestNetworkReader interestNetworkReader;
	private final InterestRelationDeleter interestRelationDeleter;

	@PostMapping("/relations")
	public ResponseEntity<CreateInterestRelationResponse> createRelation(@Auth AuthInfo authInfo, @RequestBody CreateInterestRelationRequest request) {
		UUID relationId = interestRelationCreator.create(authInfo.getPrincipalId(), request.getParentInterestId(), request.getChildInterestId());
		return ResponseEntity.status(CREATED).body(CreateInterestRelationResponse.of(relationId));
	}

	@GetMapping
	public ResponseEntity<InterestNetworkResponse> find(@Auth AuthInfo authInfo) {
		InterestNetworkDto interestNetworkDto = interestNetworkReader.read(authInfo.getPrincipalId());
		return ResponseEntity.ok(InterestNetworkResponse.from(interestNetworkDto));
	}

	@DeleteMapping("/relations/{id}")
	public ResponseEntity<Void> deleteRelation(@Auth AuthInfo authInfo, @PathVariable("id") UUID interestRelationId) {
		interestRelationDeleter.delete(authInfo.getPrincipalId(), interestRelationId);
		return ResponseEntity.noContent().build();
	}
}
