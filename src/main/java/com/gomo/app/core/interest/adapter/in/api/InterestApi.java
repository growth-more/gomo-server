package com.gomo.app.core.interest.adapter.in.api;

import static org.springframework.http.HttpStatus.*;

import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.common.session.Session;
import com.gomo.app.common.session.SessionInfo;
import com.gomo.app.core.interest.adapter.in.api.request.CreateInterestRequest;
import com.gomo.app.core.interest.adapter.in.api.request.UpdateInterestRequest;
import com.gomo.app.core.interest.adapter.in.api.response.CreateInterestResponse;
import com.gomo.app.core.interest.adapter.in.api.response.ListInterestResponse;
import com.gomo.app.core.interest.adapter.in.api.response.ReadInterestResponse;
import com.gomo.app.core.interest.application.port.dto.InterestDto;
import com.gomo.app.core.interest.application.port.in.InterestCreator;
import com.gomo.app.core.interest.application.port.in.InterestDeleter;
import com.gomo.app.core.interest.application.port.in.InterestReader;
import com.gomo.app.core.interest.application.port.in.InterestUpdater;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/interests")
@CoreApi
public class InterestApi {

	private final InterestCreator interestCreator;
	private final InterestReader interestReader;
	private final InterestUpdater interestUpdater;
	private final InterestDeleter interestDeleter;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<CreateInterestResponse> create(@Session SessionInfo sessionInfo, @ModelAttribute CreateInterestRequest request) {
		UUID interestId = interestCreator.create(request.toCommand(sessionInfo.getPrincipalId()));
		return ResponseEntity.status(CREATED).body(CreateInterestResponse.of(interestId));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ReadInterestResponse> find(@PathVariable("id") UUID interestId) {
		InterestDto dto = interestReader.read(interestId);
		return ResponseEntity.status(OK).body(ReadInterestResponse.from(dto));
	}

	@GetMapping
	public ResponseEntity<ListInterestResponse> list(@Session SessionInfo sessionInfo) {
		List<ReadInterestResponse> responses = interestReader.readAll(sessionInfo.getPrincipalId()).stream()
			.map(ReadInterestResponse::from)
			.toList();
		return ResponseEntity.status(OK).body(ListInterestResponse.of(responses));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Session SessionInfo sessionInfo, @PathVariable("id") UUID interestId, @RequestBody UpdateInterestRequest request) {
		interestUpdater.update(request.toCommand(sessionInfo.getPrincipalId(), interestId));
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@Session SessionInfo sessionInfo, @PathVariable("id") UUID interestId) {
		interestDeleter.delete(sessionInfo.getPrincipalId(), interestId);
		return ResponseEntity.noContent().build();
	}
}
