package com.gomo.app.member.application;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.image.ImageService;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.exception.MemberNotFoundException;
import com.gomo.app.member.exception.code.MemberErrorCode;
import com.gomo.app.member.presentation.response.UpdateProfileBannerResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateProfileBannerUseCase {

	private final MemberRepository memberRepository;
	private final ImageService imageService;

	public UpdateProfileBannerResponse update(MemberId memberId, MultipartFile profileBanner) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.NOT_FOUND));

		String banner_url = imageService.uploadImage(profileBanner);
		member.updateProfileBanner(member.getProfileBanner().updateUrl(banner_url));

		return UpdateProfileBannerResponse.of(banner_url);
	}
}
