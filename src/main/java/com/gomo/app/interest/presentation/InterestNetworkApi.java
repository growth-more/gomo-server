package com.gomo.app.interest.presentation;

import static org.springframework.http.HttpStatus.*;

import java.util.UUID;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.member.domain.model.MemberId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.authentication.MemberContext;
import com.gomo.app.common.authentication.SessionMember;
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

@RequiredArgsConstructor
@RequestMapping("/interests/networks")
@Presentation
public class InterestNetworkApi {

	private final CreateInterestRelationUseCase createInterestRelationUseCase;
	private final ReadInterestNetworkUseCase readInterestNetworkUseCase;
	private final DeleteInterestRelationUseCase deleteInterestRelationUseCase;

	@PostMapping("/relations")
	public ResponseEntity<CreateInterestRelationResponse> createRelation(@Auth MemberId memberId, @RequestBody CreateInterestRelationRequest request) {
		CreateInterestRelationResponse response = createInterestRelationUseCase.create(RegistrantId.of(memberId.getId()), request);
		return ResponseEntity.status(CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<InterestNetworkResponse> find(@Auth MemberId memberId) {
		InterestNetworkResponse response = readInterestNetworkUseCase.find(memberId.getId());
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/relations/{id}")
	public ResponseEntity<Void> deleteRelation(@Auth MemberId memberId, @PathVariable("id") UUID interestRelationId) {
		deleteInterestRelationUseCase.delete(memberId.getId(), InterestRelationId.of(interestRelationId));
		return ResponseEntity.noContent().build();
	}
}
