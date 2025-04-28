package com.gomo.app.member.presentation;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.Presentation;
import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
import com.gomo.app.member.application.DeleteMemberUseCase;
import com.gomo.app.member.application.UpdateProfileBannerUseCase;
import com.gomo.app.member.presentation.request.UpdateProfileBannerRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/images/banners")
@Presentation
public class ProfileBannerApi {

	private final UpdateProfileBannerUseCase updateProfileBannerUseCase;
	private final DeleteMemberUseCase deleteMemberUseCase;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @ModelAttribute UpdateProfileBannerRequest request) {
		updateProfileBannerUseCase.update(authInfo.getMemberId(), request.getProfileBanner());
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> delete(@Auth AuthInfo authInfo){
		deleteMemberUseCase.deleteBanner(authInfo.getMemberId());
		return ResponseEntity.ok().build();
	}
}
