package com.gomo.app.core.interest.adapter.in.api;

import static org.springframework.http.HttpStatus.*;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.interest.adapter.in.api.response.CreateMajorInterestResponse;
import com.gomo.app.core.interest.adapter.in.api.response.ListMajorInterestResponse;
import com.gomo.app.core.interest.application.port.dto.MajorInterestDto;
import com.gomo.app.core.interest.application.port.in.MajorInterestCreator;
import com.gomo.app.core.interest.application.port.in.MajorInterestDeleter;
import com.gomo.app.core.interest.application.port.in.MajorInterestReader;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/interests")
@CoreApi
public class MajorInterestApi {

	private final MajorInterestCreator majorInterestCreator;
	private final MajorInterestReader majorInterestReader;
	private final MajorInterestDeleter majorInterestDeleter;

	@PostMapping("/{id}/majors")
	public ResponseEntity<CreateMajorInterestResponse> create(@Auth AuthInfo authInfo, @PathVariable("id") UUID interestId) {
		UUID majorInterestId = majorInterestCreator.create(authInfo.getPrincipalId(), interestId);
		return ResponseEntity.status(CREATED).body(CreateMajorInterestResponse.of(majorInterestId));
	}

	@GetMapping("/majors")
	public ResponseEntity<ListMajorInterestResponse> findAll(@Auth AuthInfo authInfo) {
		List<MajorInterestDto> dtos = majorInterestReader.readAll(authInfo.getPrincipalId());
		return ResponseEntity.ok(ListMajorInterestResponse.of(dtos));
	}

	@DeleteMapping("/majors/{id}")
	public ResponseEntity<Void> delete(@Auth AuthInfo authInfo, @PathVariable("id") UUID majorInterestId) {
		majorInterestDeleter.delete(authInfo.getPrincipalId(), majorInterestId);
		return ResponseEntity.noContent().build();
	}
}
