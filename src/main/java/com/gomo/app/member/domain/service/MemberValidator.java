package com.gomo.app.member.domain.service;

import com.gomo.app.common.domain.DomainService;
import com.gomo.app.member.domain.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class MemberValidator {

	private final MemberRepository memberRepository;

	public void checkDuplicatedEmail(String email) {

	}

	public void checkDuplicatedHandle(String handle) {

	}
}
