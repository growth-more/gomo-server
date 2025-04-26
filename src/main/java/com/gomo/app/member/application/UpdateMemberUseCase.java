package com.gomo.app.member.application;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.domain.service.MemberValidator;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.exception.MemberNotFoundException;
import com.gomo.app.member.exception.code.MemberErrorCode;
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
				.orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.NOT_FOUND));

		member.updateMemberInfo(request.getName(), request.getMotto());
	}

	// TODO <jhl221123>: 유스케이스이기 때문에 비밀번호, 핸들 각각 분리하는 것을 고려해보자.
	public void updatePassword(MemberId memberId, UpdatePasswordRequest request) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.NOT_FOUND));
		member.updatePassword(request.getOriginPassword(), request.getUpdatedPassword(), passwordService);
	}

	public void updateHandle(MemberId memberId, UpdateHandleRequest request) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.NOT_FOUND));
		memberValidator.checkDuplicatedHandle(request.getHandle());
		member.updateHandle(request.getHandle());
	}
}
