package com.gomo.app.member.application;

import com.gomo.app.common.domain.service.ImageService;
import com.gomo.app.common.exception.DomainErrorCode;
import com.gomo.app.common.exception.PolicyViolationException;
import com.gomo.app.member.domain.model.*;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.exception.MemberAuthenticationFailedException;
import com.gomo.app.member.exception.MemberDuplicatedException;
import com.gomo.app.member.exception.MemberNotFoundException;
import com.gomo.app.member.presentation.response.UpdateProfileImageResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.presentation.request.UpdateHandleRequest;
import com.gomo.app.member.presentation.request.UpdateMemberRequest;
import com.gomo.app.member.presentation.request.UpdatePasswordRequest;
import com.gomo.app.member.presentation.request.UpdateProfileImageRequest;

import lombok.RequiredArgsConstructor;

import static com.gomo.app.member.exception.MemberErrorCode.HANDLE_DUPLICATED;
import static com.gomo.app.member.exception.MemberErrorCode.NOT_FOUND;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateMemberUseCase {

	private final MemberRepository memberRepository;
	private final PasswordService passwordService;
	private final ImageService imageService;

	public void update(MemberId memberId, UpdateMemberRequest request) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException(NOT_FOUND, "member id not found: " + memberId));

		if (request.getMotto() != null){
			Motto motto = member.getMotto().update(request.getMotto());
			member.updateMotto(motto);
		}
		if (request.getName() != null){
			MemberName name = member.getName().update(request.getName());
			member.updateName(name);
		}
	}

	public UpdateProfileImageResponse updateProfileImage(MemberId memberId, UpdateProfileImageRequest request) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException(NOT_FOUND, "member id not found: " + memberId));
		// 기존 이미지 삭제
		imageService.deleteImage(member.getProfileImage().getUrl());

		// 새 이미지 업로드
		String profile_url = imageService.uploadImage(request.getProfileImage());
		member.updateProfileImage(member.getProfileImage().updateUrl(profile_url));

		return UpdateProfileImageResponse.of(profile_url);
	}

	public void updatePassword(MemberId memberId, UpdatePasswordRequest request) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException(NOT_FOUND, "member id not found: " + memberId));
		validatePasswordNotSame(request);
		Password password = member.getPassword().update(request.getUpdatedPassword(), request.getOriginPassword(), passwordService);
		member.updatePassword(password);
	}

	public void updateHandle(MemberId memberId, UpdateHandleRequest request) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException(NOT_FOUND, "member id not found: " + memberId));
		if(isDuplicatedHandle(request.getHandle())) {
			throw new MemberDuplicatedException(HANDLE_DUPLICATED, "this handle already exists");
		}
		member.updateHandle(member.getHandle().update(request.getHandle()));
	}

	private boolean isDuplicatedHandle(String handle) {
		return memberRepository.existsByHandle(Handle.of(handle));
	}

	private static void validatePasswordNotSame(UpdatePasswordRequest request){
		if(request.getOriginPassword().equals(request.getUpdatedPassword())){
			throw new PolicyViolationException(DomainErrorCode.INVALID_PARAMETER, "update password must not same as origin password");
		}
	}
}
