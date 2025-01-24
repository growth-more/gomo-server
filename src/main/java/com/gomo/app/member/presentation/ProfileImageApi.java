package com.gomo.app.member.presentation;

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
	public ResponseEntity<UpdateProfileImageResponse> update(@RequestBody UpdateProfileImageRequest request) {
		return null;
	}
}
