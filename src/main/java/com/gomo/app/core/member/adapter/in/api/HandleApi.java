package com.gomo.app.core.member.adapter.in.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.member.adapter.in.api.request.UpdateHandleRequest;
import com.gomo.app.core.member.application.port.in.HandleUpdater;
import com.gomo.app.core.member.application.port.in.HandleValidator;
import com.gomo.app.support.auth.adapter.in.security.Auth;
import com.gomo.app.support.auth.adapter.in.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/handles")
@CoreApi
public class HandleApi {

	private final HandleValidator handleValidator;
	private final HandleUpdater handleUpdater;

	@GetMapping("/duplicate")
	public ResponseEntity<Void> checkHandleDuplicated(@RequestParam String handle) {
		handleValidator.validateDuplicated(handle);
		return ResponseEntity.ok().build();
	}

	@PutMapping
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestBody UpdateHandleRequest request) {
		handleUpdater.update(authInfo.getPrincipalId(), request.getHandle());
		return ResponseEntity.noContent().build();
	}
}
