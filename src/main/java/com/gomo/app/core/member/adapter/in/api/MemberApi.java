package com.gomo.app.core.member.adapter.in.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.member.adapter.in.api.request.UpdateMemberRequest;
import com.gomo.app.core.member.adapter.in.api.response.ReadMemberResponse;
import com.gomo.app.core.member.application.port.dto.MemberDto;
import com.gomo.app.core.member.application.port.in.MemberDeleter;
import com.gomo.app.core.member.application.port.in.MemberReader;
import com.gomo.app.core.member.application.port.in.MemberUpdater;
import com.gomo.app.core.auth.adapter.in.security.Auth;
import com.gomo.app.core.auth.adapter.in.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members")
@CoreApi
public class MemberApi {

	private final MemberReader memberReader;
	private final MemberUpdater memberUpdater;
	private final MemberDeleter memberDeleter;

	@GetMapping
	public ResponseEntity<ReadMemberResponse> read(@Auth AuthInfo authInfo) {
		MemberDto dto = memberReader.read(authInfo.getPrincipalId());
		return ResponseEntity.ok(ReadMemberResponse.of(dto));
	}

	@PutMapping
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestBody UpdateMemberRequest request) {
		memberUpdater.update(authInfo.getPrincipalId(), request.getName(), request.getMotto());
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> delete(@Auth AuthInfo authInfo) {
		memberDeleter.delete(authInfo.getPrincipalId());
		return ResponseEntity.noContent().build();
	}
}
