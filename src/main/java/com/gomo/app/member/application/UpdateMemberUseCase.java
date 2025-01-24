package com.gomo.app.member.application;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.presentation.request.UpdateHandleRequest;
import com.gomo.app.member.presentation.request.UpdateMemberRequest;
import com.gomo.app.member.presentation.request.UpdatePasswordRequest;
import com.gomo.app.member.presentation.request.UpdateProfileImageRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateMemberUseCase {

	private final MemberRepository memberRepository;

	public void update(MemberId memberId, UpdateMemberRequest request) {

	}

	public void updateProfileImage(MemberId memberId, UpdateProfileImageRequest request) {

	}

	public void updatePassword(MemberId memberId, UpdatePasswordRequest request) {

	}

	public void updateHandle(MemberId memberId, UpdateHandleRequest request) {

	}

	private boolean isDuplicatedHandle(String handle) {
		return false;
	}
}
