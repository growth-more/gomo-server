package com.gomo.app.member.domain.service;

import com.gomo.app.common.domain.service.DomainService;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.repository.MemberRepository;

import com.gomo.app.member.exception.MemberDuplicatedException;
import lombok.RequiredArgsConstructor;

import static com.gomo.app.member.exception.MemberErrorCode.EMAIL_DUPLICATED;
import static com.gomo.app.member.exception.MemberErrorCode.HANDLE_DUPLICATED;

@RequiredArgsConstructor
@DomainService
public class MemberValidator {

	private final MemberRepository memberRepository;

	public void checkDuplicatedEmail(String email) {
		if (memberRepository.existsByEmail(Email.of(email))){
			throw new MemberDuplicatedException(EMAIL_DUPLICATED, "email already exists");
		}
	}

	public void checkDuplicatedHandle(String handle) {
		if (memberRepository.existsByHandle(Handle.of(handle))){
			throw new MemberDuplicatedException(HANDLE_DUPLICATED, "handle already exists");
		}
	}
}
