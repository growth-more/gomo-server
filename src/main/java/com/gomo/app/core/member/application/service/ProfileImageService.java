package com.gomo.app.core.member.application.service;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.member.application.port.in.ProfileImageDeleter;
import com.gomo.app.core.member.application.port.in.ProfileImageUpdater;
import com.gomo.app.core.member.application.port.out.ProfileAssetUploader;
import com.gomo.app.core.member.domain.model.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class ProfileImageService implements ProfileImageUpdater, ProfileImageDeleter {

	private final MemberService memberService;
	private final ProfileAssetUploader profileAssetUploader;

	@Override
	@AuditLog(action = "PROFILE_IMAGE_UPDATE")
	public void update(UUID memberId, MultipartFile profileImage) {
		Member member = memberService.findById(memberId);
		String profileUrl = profileAssetUploader.upload(profileImage);
		member.updateProfileImage(profileUrl);
	}

	@Override
	@AuditLog(action = "PROFILE_IMAGE_DELETE")
	public void delete(UUID memberId) {
		Member member = memberService.findById(memberId);
		member.deleteProfile();
	}
}
