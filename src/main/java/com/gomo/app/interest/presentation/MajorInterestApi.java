package com.gomo.app.interest.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public ResponseEntity<CreateMajorInterestResponse> create(@RequestParam("id") InterestId interestId) {
		return null;
	}

	@GetMapping("/majors")
	public ResponseEntity<ListMajorInterestResponse> findAll() {
		return null;
	}

	@DeleteMapping("/majors/{id}")
	public ResponseEntity<Void> delete(@RequestParam("id") MajorInterestId majorInterestId) {
		return null;
	}
}
