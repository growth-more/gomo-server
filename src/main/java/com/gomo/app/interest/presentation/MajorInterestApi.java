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
	public ResponseEntity<CreateMajorInterestResponse> create(@Auth MemberId memberId, @PathVariable("id") UUID interestId) {
		CreateMajorInterestResponse response = createMajorInterestUseCase.create(memberId.getId(), InterestId.of(interestId));
		return ResponseEntity.status(CREATED).body(response);
	}

	@GetMapping("/majors")
	public ResponseEntity<ListMajorInterestResponse> findAll(@Auth MemberId memberId) {
		ListMajorInterestResponse response = readMajorInterestUseCase.findAll(memberId.getId());
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/majors/{id}")
	public ResponseEntity<Void> delete(@Auth MemberId memberId, @PathVariable("id") UUID majorInterestId) {
		deleteMajorInterestUseCase.delete(memberId.getId(), MajorInterestId.of(majorInterestId));
		return ResponseEntity.noContent().build();
	}
}
