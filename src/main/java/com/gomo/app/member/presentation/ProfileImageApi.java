package com.gomo.app.member.presentation;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.member.application.UpdateMemberUseCase;
import com.gomo.app.member.domain.model.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/members/images/profiles")
@Presentation
public class ProfileImageApi {

	private final UpdateMemberUseCase updateMemberUseCase;

	@PutMapping
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestPart MultipartFile profileImage) {
		updateMemberUseCase.updateProfileImage(MemberId.of(authInfo.getMemberId()), profileImage);
		return ResponseEntity.noContent().build();
	}
}
