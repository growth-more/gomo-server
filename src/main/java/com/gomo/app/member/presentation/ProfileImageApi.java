package com.gomo.app.member.presentation;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.member.domain.model.MemberId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.member.application.UpdateMemberUseCase;
import com.gomo.app.member.presentation.request.UpdateProfileImageRequest;
import com.gomo.app.member.presentation.response.UpdateProfileImageResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/images/profiles")
@Presentation
public class ProfileImageApi {

	private final UpdateMemberUseCase updateMemberUseCase;

	@PutMapping
	public ResponseEntity<Void> update(@Auth MemberId memberId, @RequestBody UpdateProfileImageRequest request) {
		updateMemberUseCase.updateProfileImage(memberId, request);
		return ResponseEntity.noContent().build();
	}
}
