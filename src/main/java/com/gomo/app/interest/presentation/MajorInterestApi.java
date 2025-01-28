package com.gomo.app.interest.presentation;

import static org.springframework.http.HttpStatus.*;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.authentication.MemberContext;
import com.gomo.app.common.authentication.SessionMember;
import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.interest.application.CreateMajorInterestUseCase;
import com.gomo.app.interest.application.DeleteMajorInterestUseCase;
import com.gomo.app.interest.application.ReadMajorInterestUseCase;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.MajorInterestId;
import com.gomo.app.interest.presentation.response.CreateMajorInterestResponse;
import com.gomo.app.interest.presentation.response.ListMajorInterestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/interests")
@Presentation
public class MajorInterestApi {

	private final CreateMajorInterestUseCase createMajorInterestUseCase;
	private final ReadMajorInterestUseCase readMajorInterestUseCase;
	private final DeleteMajorInterestUseCase deleteMajorInterestUseCase;

	@PostMapping("/{id}/majors")
	public ResponseEntity<CreateMajorInterestResponse> create(@PathVariable("id") UUID interestId) {
		SessionMember sessionMember = MemberContext.getSessionMember();
		CreateMajorInterestResponse response = createMajorInterestUseCase.create(sessionMember.getId(), InterestId.of(interestId));
		return ResponseEntity.status(CREATED).body(response);
	}

	@GetMapping("/majors")
	public ResponseEntity<ListMajorInterestResponse> findAll() {
		SessionMember sessionMember = MemberContext.getSessionMember();
		ListMajorInterestResponse response = readMajorInterestUseCase.findAll(sessionMember.getId());
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/majors/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") UUID majorInterestId) {
		SessionMember sessionMember = MemberContext.getSessionMember();
		deleteMajorInterestUseCase.delete(sessionMember.getId(), MajorInterestId.of(majorInterestId));
		return ResponseEntity.noContent().build();
	}
}
