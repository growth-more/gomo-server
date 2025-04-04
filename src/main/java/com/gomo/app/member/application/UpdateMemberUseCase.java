package com.gomo.app.member.application;

import com.gomo.app.common.domain.service.ImageService;
import com.gomo.app.common.exception.DomainErrorCode;
import com.gomo.app.common.exception.PolicyViolationException;
import com.gomo.app.member.domain.model.*;
import com.gomo.app.member.domain.service.MemberValidator;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.exception.MemberAuthenticationFailedException;
import com.gomo.app.member.exception.MemberDuplicatedException;
import com.gomo.app.member.exception.MemberNotFoundException;
import com.gomo.app.member.presentation.response.UpdateProfileBannerResponse;
import com.gomo.app.member.presentation.response.UpdateProfileImageResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.presentation.request.UpdateHandleRequest;
import com.gomo.app.member.presentation.request.UpdateMemberRequest;
import com.gomo.app.member.presentation.request.UpdatePasswordRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import static com.gomo.app.member.exception.MemberErrorCode.HANDLE_DUPLICATED;
import static com.gomo.app.member.exception.MemberErrorCode.NOT_FOUND;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateMemberUseCase {

	private final MemberRepository memberRepository;
	private final PasswordService passwordService;
	private final ImageService imageService;
	private final MemberValidator memberValidator;

	public void update(MemberId memberId, UpdateMemberRequest request) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException(NOT_FOUND, "member id not found: " + memberId));

		member.updateMemberInfo(request.getName(), request.getMotto());
	}

	public UpdateProfileImageResponse updateProfileImage(MemberId memberId, MultipartFile profileImage) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException(NOT_FOUND, "member id not found: " + memberId));

		String profile_url = imageService.uploadImage(profileImage);
		member.updateProfileImage(member.getProfileImage().updateUrl(profile_url));

		return UpdateProfileImageResponse.of(profile_url);
	}

	public UpdateProfileBannerResponse updateProfileBanner(MemberId memberId, MultipartFile profileBanner) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException(NOT_FOUND, "member id not found: " + memberId));

		String banner_url = imageService.uploadImage(profileBanner);
		member.updateProfileBanner(member.getProfileBanner().updateUrl(banner_url));

		return UpdateProfileBannerResponse.of(banner_url);
	}

	public void updatePassword(MemberId memberId, UpdatePasswordRequest request) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException(NOT_FOUND, "member id not found: " + memberId));
		member.updatePassword(request.getOriginPassword(), request.getUpdatedPassword(), passwordService);
	}

	public void updateHandle(MemberId memberId, UpdateHandleRequest request) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException(NOT_FOUND, "member id not found: " + memberId));
		memberValidator.checkDuplicatedHandle(request.getHandle());
		member.updateHandle(request.getHandle());
	}
}
