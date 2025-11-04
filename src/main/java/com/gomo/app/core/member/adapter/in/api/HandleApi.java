package com.gomo.app.core.member.adapter.in.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.common.session.Session;
import com.gomo.app.common.session.SessionInfo;
import com.gomo.app.core.member.adapter.in.api.request.UpdateHandleRequest;
import com.gomo.app.core.member.application.port.in.HandleUpdater;
import com.gomo.app.core.member.application.port.in.HandleValidator;

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
	public ResponseEntity<Void> update(@Session SessionInfo sessionInfo, @RequestBody UpdateHandleRequest request) {
		handleUpdater.update(sessionInfo.getPrincipalId(), request.getHandle());
		return ResponseEntity.noContent().build();
	}
}
