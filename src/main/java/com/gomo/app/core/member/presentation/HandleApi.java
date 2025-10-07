package com.gomo.app.core.member.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.member.application.usecase.CheckHandleUseCase;
import com.gomo.app.core.member.application.usecase.UpdateHandleUseCase;
import com.gomo.app.core.member.presentation.request.UpdateHandleRequest;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/handles")
@CoreApi
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
