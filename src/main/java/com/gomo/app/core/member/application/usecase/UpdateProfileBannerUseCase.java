package com.gomo.app.core.member.application.usecase;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.member.application.port.dto.UpdateProfileBannerDto;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.support.image.application.port.UploadImagePortIn;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
public class UpdateProfileBannerUseCase {

	private final UploadImagePortIn uploadImagePortIn;
	private final MemberService memberService;

	@AuditLog(action = "UPDATE_PROFILE_BANNER")
	public UpdateProfileBannerDto update(UUID memberId, MultipartFile profileBanner) {
		Member member = memberService.find(memberId);
		String bannerUrl = uploadImagePortIn.upload(profileBanner).orElse(null);
		member.updateProfileBanner(bannerUrl);
		return UpdateProfileBannerDto.of(bannerUrl);
	}
}
