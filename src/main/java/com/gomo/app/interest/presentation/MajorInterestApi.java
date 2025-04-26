package com.gomo.app.interest.presentation;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
import com.gomo.app.common.Presentation;
import com.gomo.app.interest.application.CreateMajorInterestUseCase;
import com.gomo.app.interest.application.DeleteMajorInterestUseCase;
import com.gomo.app.interest.application.ReadMajorInterestUseCase;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.MajorInterestId;
import com.gomo.app.interest.presentation.response.CreateMajorInterestResponse;
import com.gomo.app.interest.presentation.response.ListMajorInterestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/interests")
@Presentation
public class MajorInterestApi {

	private final CreateMajorInterestUseCase createMajorInterestUseCase;
	private final ReadMajorInterestUseCase readMajorInterestUseCase;
	private final DeleteMajorInterestUseCase deleteMajorInterestUseCase;

	@PostMapping("/{id}/majors")
	public ResponseEntity<CreateMajorInterestResponse> create(@Auth AuthInfo authInfo, @PathVariable("id") UUID interestId) {
		CreateMajorInterestResponse response = createMajorInterestUseCase.create(authInfo.getMemberId(), InterestId.of(interestId));
		return ResponseEntity.status(CREATED).body(response);
	}

	@GetMapping("/majors")
	public ResponseEntity<ListMajorInterestResponse> findAll(@Auth AuthInfo authInfo) {
		ListMajorInterestResponse response = readMajorInterestUseCase.findAll(authInfo.getMemberId());
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/majors/{id}")
	public ResponseEntity<Void> delete(@Auth AuthInfo authInfo, @PathVariable("id") UUID majorInterestId) {
		deleteMajorInterestUseCase.delete(authInfo.getMemberId(), MajorInterestId.of(majorInterestId));
		return ResponseEntity.noContent().build();
	}
}
