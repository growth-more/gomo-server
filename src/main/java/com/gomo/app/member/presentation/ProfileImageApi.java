package com.gomo.app.member.presentation;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.member.application.DeleteMemberUseCase;
import com.gomo.app.member.application.UpdateProfileImageUseCase;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.presentation.request.UpdateProfileImageRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/images/profiles")
@Presentation
public class ProfileImageApi {

	private final UpdateProfileImageUseCase updateProfileImageUseCase;
	private final DeleteMemberUseCase deleteMemberUseCase;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @ModelAttribute UpdateProfileImageRequest request) {
		updateProfileImageUseCase.update(MemberId.of(authInfo.getMemberId()), request.getProfileImage());
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> delete(@Auth AuthInfo authInfo){
		deleteMemberUseCase.deleteProfile(MemberId.of(authInfo.getMemberId()));
		return ResponseEntity.ok().build();
	}
}
