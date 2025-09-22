package com.gomo.app.member.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gomo.app.common.Presentation;
import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
import com.gomo.app.member.application.CheckHandleUseCase;
import com.gomo.app.member.application.UpdateHandleUseCase;
import com.gomo.app.member.presentation.request.UpdateHandleRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/handles")
@Presentation
public class HandleApi {

	private final CheckHandleUseCase checkHandleUseCase;
	private final UpdateHandleUseCase updateHandleUseCase;

	@GetMapping("/duplicate")
	public ResponseEntity<Void> checkHandleDuplicated(@RequestParam String handle) {
		checkHandleUseCase.checkHandleDuplicated(handle);
		return ResponseEntity.ok().build();
	}

	@PutMapping
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestBody UpdateHandleRequest request) {
		updateHandleUseCase.update(authInfo.getMemberId(), request.getHandle());
		return ResponseEntity.noContent().build();
	}
}
