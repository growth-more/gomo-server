package com.gomo.app.core.member.application.service;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.member.domain.exception.EmailDuplicatedException;
import com.gomo.app.core.member.domain.exception.code.EmailErrorCode;
import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.domain.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class EmailService {

	private final MemberRepository memberRepository;

	void validateDuplicated(String email) {
		memberRepository.findByEmail(Email.of(email)).ifPresent(m -> {
			throw new EmailDuplicatedException(EmailErrorCode.DUPLICATED);
		});
	}
}
