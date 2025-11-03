package com.gomo.app.core.member.application.service;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.member.application.port.in.ProfileBannerDeleter;
import com.gomo.app.core.member.application.port.in.ProfileBannerUpdater;
import com.gomo.app.core.member.application.port.out.ProfileAssetUploader;
import com.gomo.app.core.member.domain.model.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class ProfileBannerService implements ProfileBannerUpdater, ProfileBannerDeleter {

	private final MemberService memberService;
	private final ProfileAssetUploader profileAssetUploader;

	@Override
	@AuditLog(action = "PROFILE_BANNER_UPDATE")
	public void update(UUID memberId, MultipartFile profileBanner) {
		Member member = memberService.findById(memberId);
		String bannerUrl = profileAssetUploader.upload(profileBanner);
		member.updateProfileBanner(bannerUrl);
	}

	@Override
	@AuditLog(action = "PROFILE_BANNER_DELETE")
	public void delete(UUID memberId) {
		Member member = memberService.findById(memberId);
		member.deleteBanner();
	}
}
