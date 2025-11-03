package com.gomo.app.core.member.adapter.in.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.member.adapter.in.api.request.UpdateProfileImageRequest;
import com.gomo.app.core.member.application.port.in.ProfileImageDeleter;
import com.gomo.app.core.member.application.port.in.ProfileImageUpdater;
import com.gomo.app.core.auth.adapter.in.security.Auth;
import com.gomo.app.core.auth.adapter.in.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/images/profiles")
@CoreApi
public class ProfileImageApi {

	private final ProfileImageUpdater profileImageUpdater;
	private final ProfileImageDeleter profileImageDeleter;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @ModelAttribute UpdateProfileImageRequest request) {
		profileImageUpdater.update(authInfo.getPrincipalId(), request.getProfileImage());
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> delete(@Auth AuthInfo authInfo) {
		profileImageDeleter.delete(authInfo.getPrincipalId());
		return ResponseEntity.ok().build();
	}
}
