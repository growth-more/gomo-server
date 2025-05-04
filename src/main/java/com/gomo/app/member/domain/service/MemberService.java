package com.gomo.app.member.domain.service;

import com.gomo.app.common.DomainService;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.exception.ActivateStatusException;
import com.gomo.app.member.exception.EmailDuplicatedException;
import com.gomo.app.member.exception.HandleDuplicatedException;
import com.gomo.app.member.exception.MemberNotFoundException;
import com.gomo.app.member.exception.code.ActivateStatusErrorCode;
import com.gomo.app.member.exception.code.EmailErrorCode;
import com.gomo.app.member.exception.code.HandleErrorCode;
import com.gomo.app.member.exception.code.MemberErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class MemberService {

	private final MemberRepository memberRepository;

	public Member find(MemberId memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.NOT_FOUND));
	}

	public Member findByEmail(Email email) {
		return memberRepository.findByEmail(email)
			.orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.NOT_FOUND));
	}

	public void checkEmailDuplicated(Email email) {
		memberRepository.findByEmail(email)
			.ifPresent(m -> {
				throw new EmailDuplicatedException(EmailErrorCode.DUPLICATED);
			});
	}

	public void checkHandleDuplicated(Handle handle) {
		memberRepository.findByHandle(handle)
			.ifPresent(m -> {
				throw new HandleDuplicatedException(HandleErrorCode.DUPLICATED);
			});
	}

	public void checkActivated(Member member) {
		switch (member.getActivateStatus()) {
			case DELETED -> throw new ActivateStatusException(ActivateStatusErrorCode.DELETED);
			case BLOCKED -> throw new ActivateStatusException(ActivateStatusErrorCode.BLOCKED);
		}
	}
}
