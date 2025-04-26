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
import com.gomo.app.member.presentation.response.UpdateProfileImageResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateProfileImageUseCase {

	private final MemberRepository memberRepository;
	private final ImageService imageService;

	public UpdateProfileImageResponse update(MemberId memberId, MultipartFile profileImage) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.NOT_FOUND));

		String profile_url = imageService.uploadImage(profileImage);
		member.updateProfileImage(member.getProfileImage().updateUrl(profile_url));

		return UpdateProfileImageResponse.of(profile_url);
	}
}
