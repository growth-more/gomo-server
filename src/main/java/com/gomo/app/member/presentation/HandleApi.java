package com.gomo.app.member.presentation;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
import com.gomo.app.common.Presentation;
import com.gomo.app.member.application.ReadMemberUseCase;
import com.gomo.app.member.application.UpdateMemberUseCase;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.presentation.request.UpdateHandleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/members/handles")
@Presentation
public class HandleApi {

	private final ReadMemberUseCase readMemberUseCase;
	private final UpdateMemberUseCase updateMemberUseCase;

	@GetMapping("/duplicate")
	public ResponseEntity<Void> checkDuplicate(@RequestParam String handle) {
		readMemberUseCase.checkDuplicate(handle);
		return ResponseEntity.ok().build();
	}

	@PutMapping
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestBody UpdateHandleRequest request) {
		updateMemberUseCase.updateHandle(MemberId.of(authInfo.getMemberId()), request);
		return ResponseEntity.noContent().build();
	}
}
