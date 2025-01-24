package com.gomo.app.member.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.member.application.ReadMemberUseCase;
import com.gomo.app.member.application.UpdateMemberUseCase;
import com.gomo.app.member.presentation.request.UpdateHandleRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/handles")
@Presentation
public class HandleApi {

	private final ReadMemberUseCase readMemberUseCase;
	private final UpdateMemberUseCase updateMemberUseCase;

	@GetMapping("/duplicate")
	public ResponseEntity<Void> checkDuplicate(@RequestParam String handle) {
		return null;
	}

	@PutMapping
	public ResponseEntity<Void> update(@RequestBody UpdateHandleRequest request) {
		return null;
	}
}
