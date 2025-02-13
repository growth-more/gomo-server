package com.gomo.app.member.domain.service;

import com.gomo.app.common.domain.service.DomainService;
import com.gomo.app.member.domain.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

import java.util.Random;

@RequiredArgsConstructor
@DomainService
public class EmailAuthCodeService {

	private final MemberRepository memberRepository;

	public String create(String email) {
		return null;
	}

	public String verify(String code){
		return null;
	}
}
