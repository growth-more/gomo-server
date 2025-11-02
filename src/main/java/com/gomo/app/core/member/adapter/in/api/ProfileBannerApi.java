package com.gomo.app.core.member.adapter.in.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.member.adapter.in.api.request.UpdateProfileBannerRequest;
import com.gomo.app.core.member.application.port.in.ProfileBannerDeleter;
import com.gomo.app.core.member.application.port.in.ProfileBannerUpdater;
import com.gomo.app.support.auth.adapter.in.security.Auth;
import com.gomo.app.support.auth.adapter.in.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/images/banners")
@CoreApi
public class ProfileBannerApi {

	private final ProfileBannerUpdater profileBannerUpdater;
	private final ProfileBannerDeleter profileBannerDeleter;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @ModelAttribute UpdateProfileBannerRequest request) {
		profileBannerUpdater.update(authInfo.getPrincipalId(), request.getProfileBanner());
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> delete(@Auth AuthInfo authInfo) {
		profileBannerDeleter.delete(authInfo.getPrincipalId());
		return ResponseEntity.ok().build();
	}
}
