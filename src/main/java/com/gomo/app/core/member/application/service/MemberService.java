package com.gomo.app.core.member.application.service;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.member.application.port.dto.MemberDto;
import com.gomo.app.core.member.application.port.in.MemberDeleter;
import com.gomo.app.core.member.application.port.in.MemberReader;
import com.gomo.app.core.member.application.port.in.MemberUpdater;
import com.gomo.app.core.member.application.port.out.MemberLogoutProcessor;
import com.gomo.app.core.member.application.port.out.PointBalanceReader;
import com.gomo.app.core.member.domain.exception.MemberNotFoundException;
import com.gomo.app.core.member.domain.exception.code.MemberErrorCode;
import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class MemberService implements MemberReader, MemberUpdater, MemberDeleter {

	private final MemberRepository memberRepository;
	private final PointBalanceReader pointBalanceReader;
	private final MemberLogoutProcessor memberLogoutProcessor;

	@Override
	@Transactional(readOnly = true)
	public MemberDto read(UUID id) {
		Member member = findById(id);
		int balance = pointBalanceReader.read(member.getId());
		return MemberDto.from(member, balance);
	}

	Member findById(UUID id) {
		return memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.NOT_FOUND));
	}

	Member findByEmail(String email) {
		return memberRepository.findByEmail(Email.of(email)).orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.NOT_FOUND));
	}

	@Override
	@AuditLog(action = "UPDATE_MEMBER")
	public void update(UUID id, String name, String motto) {
		Member member = findById(id);
		member.updateMemberInfo(name, motto);
	}

	@Override
	@AuditLog(action = "DELETE_MEMBER")
	public void delete(UUID memberId) {
		Member member = findById(memberId);
		memberLogoutProcessor.logout(memberId);
		member.delete();
	}
}
