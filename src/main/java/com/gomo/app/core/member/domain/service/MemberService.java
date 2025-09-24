package com.gomo.app.core.member.domain.service;

import java.util.UUID;

import com.gomo.app.common.DomainService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.domain.model.Handle;
import com.gomo.app.core.member.domain.model.LoginProvider;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.model.MemberId;
import com.gomo.app.core.member.domain.model.MemberName;
import com.gomo.app.core.member.domain.model.OAuthUserInfo;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.core.member.exception.ActivateStatusException;
import com.gomo.app.core.member.exception.EmailDuplicatedException;
import com.gomo.app.core.member.exception.HandleDuplicatedException;
import com.gomo.app.core.member.exception.MemberNotFoundException;
import com.gomo.app.core.member.exception.code.ActivateStatusErrorCode;
import com.gomo.app.core.member.exception.code.EmailErrorCode;
import com.gomo.app.core.member.exception.code.HandleErrorCode;
import com.gomo.app.core.member.exception.code.MemberErrorCode;

import jakarta.transaction.Transactional;
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

	@Transactional
	public Member oauthCreateMember(OAuthUserInfo userInfo, String provider) {
		UUID uuid = UUIDGenerator.generate();

		Member member = Member.of(
			MemberId.of(uuid),
			Email.of(userInfo.getEmail()),
			null,
			null,
			MemberName.of(userInfo.getName()),
			null,
			LoginProvider.valueOf(provider.toUpperCase())
		);

		return memberRepository.save(member);
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
