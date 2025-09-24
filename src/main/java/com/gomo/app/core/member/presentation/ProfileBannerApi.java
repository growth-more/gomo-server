package com.gomo.app.core.member.presentation;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.Presentation;
import com.gomo.app.support.auth.Auth;
import com.gomo.app.support.auth.AuthInfo;
import com.gomo.app.core.member.application.DeleteProfileBannerUseCase;
import com.gomo.app.core.member.application.UpdateProfileBannerUseCase;
import com.gomo.app.core.member.presentation.request.UpdateProfileBannerRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/images/banners")
@Presentation
public class ProfileBannerApi {

	private final UpdateProfileBannerUseCase updateProfileBannerUseCase;
	private final DeleteProfileBannerUseCase deleteProfileBannerUseCase;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @ModelAttribute UpdateProfileBannerRequest request) {
		updateProfileBannerUseCase.update(authInfo.getMemberId(), request.getProfileBanner());
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> delete(@Auth AuthInfo authInfo) {
		deleteProfileBannerUseCase.delete(authInfo.getMemberId());
		return ResponseEntity.ok().build();
	}
}
