package com.gomo.app.core.member.presentation;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.Presentation;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;
import com.gomo.app.core.member.application.usecase.DeleteProfileImageUseCase;
import com.gomo.app.core.member.application.usecase.UpdateProfileImageUseCase;
import com.gomo.app.core.member.presentation.request.UpdateProfileImageRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/images/profiles")
@Presentation
public class ProfileImageApi {

	private final UpdateProfileImageUseCase updateProfileImageUseCase;
	private final DeleteProfileImageUseCase deleteProfileImageUseCase;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @ModelAttribute UpdateProfileImageRequest request) {
		updateProfileImageUseCase.update(authInfo.getMemberId(), request.getProfileImage());
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> delete(@Auth AuthInfo authInfo) {
		deleteProfileImageUseCase.delete(authInfo.getMemberId());
		return ResponseEntity.ok().build();
	}
}
