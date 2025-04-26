package com.gomo.app.member.domain.service;

import com.gomo.app.common.DomainService;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.exception.EmailDuplicatedException;
import com.gomo.app.member.exception.HandleDuplicatedException;
import com.gomo.app.member.exception.code.EmailErrorCode;
import com.gomo.app.member.exception.code.HandleErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class MemberValidator {

	private final MemberRepository memberRepository;

	public void checkDuplicatedEmail(String email) {
		if (memberRepository.existsByEmail(Email.of(email))){
			throw new EmailDuplicatedException(EmailErrorCode.DUPLICATED);
		}
	}

	public void checkDuplicatedHandle(String handle) {
		if (memberRepository.existsByHandle(Handle.of(handle))){
			throw new HandleDuplicatedException(HandleErrorCode.DUPLICATED);
		}
	}
}
