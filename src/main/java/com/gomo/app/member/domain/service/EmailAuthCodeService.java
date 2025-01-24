package com.gomo.app.member.domain.service;

import com.gomo.app.common.domain.DomainService;
import com.gomo.app.member.domain.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class EmailAuthCodeService {

	private final MemberRepository memberRepository;

	public String create(String email) {
		return null;
	}
}
