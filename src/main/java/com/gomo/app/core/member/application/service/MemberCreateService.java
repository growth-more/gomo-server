package com.gomo.app.core.member.application.service;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.member.application.port.command.CreateMemberCommand;
import com.gomo.app.core.member.application.port.in.MemberCreator;
import com.gomo.app.core.member.application.port.out.MemberCreateEventPublisher;
import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.domain.model.Handle;
import com.gomo.app.core.member.domain.model.LoginProvider;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.model.MemberName;
import com.gomo.app.core.member.domain.model.Motto;
import com.gomo.app.core.member.domain.model.Password;
import com.gomo.app.core.member.domain.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class MemberCreateService implements MemberCreator {

	private final PasswordService passwordService;
	private final HandleService handleService;
	private final EmailService emailService;
	private final MemberCreateEventPublisher memberCreateEventPublisher;
	private final MemberRepository memberRepository;

	@Override
	@AuditLog(action = "CREATE_MEMBER")
	public UUID create(CreateMemberCommand command) {
		String email = command.email();
		emailService.validateDuplicated(command.email());

		String handle = command.handle();
		handleService.validateDuplicated(handle);

		UUID memberId = UUIDGenerator.generate();
		Password encodedPassword = passwordService.encode(command.loginProvider(), command.password(), memberId);
		Member member = Member.of(
			memberId,
			Email.of(email),
			encodedPassword,
			Handle.of(handle),
			MemberName.of(command.name()),
			Motto.of(command.motto()),
			LoginProvider.valueOf(command.loginProvider())
		);

		Member savedMember = memberRepository.save(member);
		memberCreateEventPublisher.publish(savedMember.getId());
		return savedMember.getId();
	}
}
