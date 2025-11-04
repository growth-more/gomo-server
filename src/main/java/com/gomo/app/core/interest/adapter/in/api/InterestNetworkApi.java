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
import com.gomo.app.common.session.Session;
import com.gomo.app.common.session.SessionInfo;
import com.gomo.app.core.interest.adapter.in.api.request.CreateInterestRelationRequest;
import com.gomo.app.core.interest.adapter.in.api.response.CreateInterestRelationResponse;
import com.gomo.app.core.interest.adapter.in.api.response.InterestNetworkResponse;
import com.gomo.app.core.interest.application.port.dto.InterestNetworkDto;
import com.gomo.app.core.interest.application.port.in.InterestNetworkReader;
import com.gomo.app.core.interest.application.port.in.InterestRelationCreator;
import com.gomo.app.core.interest.application.port.in.InterestRelationDeleter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/interests/networks")
@CoreApi
public class InterestNetworkApi {

	private final InterestRelationCreator interestRelationCreator;
	private final InterestNetworkReader interestNetworkReader;
	private final InterestRelationDeleter interestRelationDeleter;

	@PostMapping("/relations")
	public ResponseEntity<CreateInterestRelationResponse> createRelation(@Session SessionInfo sessionInfo, @RequestBody CreateInterestRelationRequest request) {
		UUID relationId = interestRelationCreator.create(sessionInfo.getPrincipalId(), request.getParentInterestId(), request.getChildInterestId());
		return ResponseEntity.status(CREATED).body(CreateInterestRelationResponse.of(relationId));
	}

	@GetMapping
	public ResponseEntity<InterestNetworkResponse> find(@Session SessionInfo sessionInfo) {
		InterestNetworkDto interestNetworkDto = interestNetworkReader.read(sessionInfo.getPrincipalId());
		return ResponseEntity.ok(InterestNetworkResponse.from(interestNetworkDto));
	}

	@DeleteMapping("/relations/{id}")
	public ResponseEntity<Void> deleteRelation(@Session SessionInfo sessionInfo, @PathVariable("id") UUID interestRelationId) {
		interestRelationDeleter.delete(sessionInfo.getPrincipalId(), interestRelationId);
		return ResponseEntity.noContent().build();
	}
}
