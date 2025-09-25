package com.gomo.app.core.member.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.Presentation;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;
import com.gomo.app.core.member.application.usecase.CreateMemberUseCase;
import com.gomo.app.core.member.application.usecase.DeleteMemberUseCase;
import com.gomo.app.core.member.application.usecase.UpdateMemberUseCase;
import com.gomo.app.core.member.application.port.ReadMemberPortIn;
import com.gomo.app.core.member.application.port.dto.CreateMemberDto;
import com.gomo.app.core.member.application.port.dto.MemberDto;
import com.gomo.app.core.member.presentation.request.CreateMemberRequest;
import com.gomo.app.core.member.presentation.request.UpdateMemberRequest;
import com.gomo.app.core.member.presentation.response.CreateMemberResponse;
import com.gomo.app.core.member.presentation.response.ReadMemberResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members")
@Presentation
public class MemberApi {

	private final CreateMemberUseCase createMemberUseCase;
	private final ReadMemberPortIn readMemberPortIn;
	private final UpdateMemberUseCase updateMemberUseCase;
	private final DeleteMemberUseCase deleteMemberUseCase;

	@PostMapping
	public ResponseEntity<CreateMemberResponse> create(@RequestBody CreateMemberRequest request) {
		CreateMemberDto dto = createMemberUseCase.create(request.toCommand());
		return ResponseEntity.status(HttpStatus.CREATED).body(CreateMemberResponse.of(dto.id()));
	}

	@GetMapping
	public ResponseEntity<ReadMemberResponse> read(@Auth AuthInfo authInfo) {
		MemberDto dto = readMemberPortIn.find(authInfo.getMemberId());
		return ResponseEntity.ok(ReadMemberResponse.of(dto));
	}

	@PutMapping
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestBody UpdateMemberRequest request) {
		updateMemberUseCase.update(authInfo.getMemberId(), request.getName(), request.getMotto());
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> delete(@Auth AuthInfo authInfo) {
		deleteMemberUseCase.delete(authInfo.getMemberId());
		return ResponseEntity.noContent().build();
	}
}
