package com.gomo.app.member.domain.service;

import com.gomo.app.member.domain.repository.EmailAuthCodeRepository;
import com.gomo.app.common.DomainService;
import com.gomo.app.member.exception.MemberAuthenticationFailedException;
import com.gomo.app.member.exception.code.MemberErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class EmailAuthCodeService {
	private final EmailAuthCodeRepository emailAuthCodeRepository;

	public String find(String email) {
		return emailAuthCodeRepository.findByEmail(email)
			.orElseThrow(() -> new MemberAuthenticationFailedException(MemberErrorCode.AUTHENTICATION_FAILED));
	}

	public void remove(String email) {
		emailAuthCodeRepository.delete(email);
	}
}
