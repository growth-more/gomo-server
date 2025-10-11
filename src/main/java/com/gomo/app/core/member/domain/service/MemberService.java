package com.gomo.app.core.member.domain.service;

import java.util.UUID;

import com.gomo.app.common.arch.DomainService;
import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.domain.model.Handle;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.core.member.exception.EmailDuplicatedException;
import com.gomo.app.core.member.exception.HandleDuplicatedException;
import com.gomo.app.core.member.exception.MemberNotFoundException;
import com.gomo.app.core.member.exception.code.EmailErrorCode;
import com.gomo.app.core.member.exception.code.HandleErrorCode;
import com.gomo.app.core.member.exception.code.MemberErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class MemberService {

	private final MemberRepository memberRepository;

	public Member find(UUID memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.NOT_FOUND));
	}

	public Member findByEmail(Email email) {
		return memberRepository.findByEmail(email)
			.orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.NOT_FOUND));
	}

	public void checkEmailDuplicated(Email email) {
		memberRepository.findByEmail(email).ifPresent(m -> {
			throw new EmailDuplicatedException(EmailErrorCode.DUPLICATED);
		});
	}

	public void checkHandleDuplicated(Handle handle) {
		memberRepository.findByHandle(handle).ifPresent(m -> {
			throw new HandleDuplicatedException(HandleErrorCode.DUPLICATED);
		});
	}
}
