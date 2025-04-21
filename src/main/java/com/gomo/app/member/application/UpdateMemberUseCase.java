package com.gomo.app.member.application;

import static com.gomo.app.member.exception.MemberErrorCode.*;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.domain.service.MemberValidator;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.exception.MemberNotFoundException;
import com.gomo.app.member.presentation.request.UpdateHandleRequest;
import com.gomo.app.member.presentation.request.UpdateMemberRequest;
import com.gomo.app.member.presentation.request.UpdatePasswordRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateMemberUseCase {

	private final MemberRepository memberRepository;
	private final PasswordService passwordService;
	private final MemberValidator memberValidator;

	public void update(MemberId memberId, UpdateMemberRequest request) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException(NOT_FOUND, "member id not found: " + memberId));

		member.updateMemberInfo(request.getName(), request.getMotto());
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
