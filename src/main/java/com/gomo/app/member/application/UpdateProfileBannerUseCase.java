package com.gomo.app.member.application;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.image.ImageService;
import com.gomo.app.logging.AuditLog;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.presentation.response.UpdateProfileBannerResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateProfileBannerUseCase {

	private final MemberService memberService;
	private final ImageService imageService;

	@AuditLog(action = "UPDATE_PROFILE_BANNER")
	public UpdateProfileBannerResponse update(UUID memberId, MultipartFile profileBanner) {
		Member member = memberService.find(MemberId.of(memberId));
		String updatedUrl = imageService.uploadImage(profileBanner);
		member.updateProfileBanner(updatedUrl);

		return UpdateProfileBannerResponse.of(updatedUrl);
	}
}
