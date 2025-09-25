package com.gomo.app.core.interest.presentation;

import static org.springframework.http.HttpStatus.*;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.Presentation;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;
import com.gomo.app.core.interest.application.CreateMajorInterestUseCase;
import com.gomo.app.core.interest.application.DeleteMajorInterestUseCase;
import com.gomo.app.core.interest.application.ReadMajorInterestUseCase;
import com.gomo.app.core.interest.application.port.dto.CreateMajorInterestDto;
import com.gomo.app.core.interest.application.port.dto.MajorInterestDto;
import com.gomo.app.core.interest.presentation.response.CreateMajorInterestResponse;
import com.gomo.app.core.interest.presentation.response.ListMajorInterestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/interests")
@Presentation
public class MajorInterestApi {

	private final CreateMajorInterestUseCase createMajorInterestUseCase;
	private final ReadMajorInterestUseCase readMajorInterestUseCase;
	private final DeleteMajorInterestUseCase deleteMajorInterestUseCase;

	@PostMapping("/{id}/majors")
	public ResponseEntity<CreateMajorInterestResponse> create(@Auth AuthInfo authInfo, @PathVariable("id") UUID interestId) {
		CreateMajorInterestDto dto = createMajorInterestUseCase.create(authInfo.getMemberId(), interestId);
		return ResponseEntity.status(CREATED).body(CreateMajorInterestResponse.of(dto.id()));
	}

	@GetMapping("/majors")
	public ResponseEntity<ListMajorInterestResponse> findAll(@Auth AuthInfo authInfo) {
		List<MajorInterestDto> dtos = readMajorInterestUseCase.findAll(authInfo.getMemberId());
		return ResponseEntity.ok(ListMajorInterestResponse.of(dtos));
	}

	@DeleteMapping("/majors/{id}")
	public ResponseEntity<Void> delete(@Auth AuthInfo authInfo, @PathVariable("id") UUID majorInterestId) {
		deleteMajorInterestUseCase.delete(authInfo.getMemberId(), majorInterestId);
		return ResponseEntity.noContent().build();
	}
}
