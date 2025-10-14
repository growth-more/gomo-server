package com.gomo.app.core.member.application.usecase;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.security.encoder.application.port.EncodePasswordPortIn;
import com.gomo.app.common.security.jwt.application.port.VerifyJwtPortIn;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.member.application.port.command.CreateMemberCommand;
import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.domain.model.Handle;
import com.gomo.app.core.member.domain.model.LoginProvider;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.model.MemberName;
import com.gomo.app.core.member.domain.model.Motto;
import com.gomo.app.core.member.domain.model.Password;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.core.point.application.port.CreatePointWalletPortIn;
import com.gomo.app.core.streak.application.port.CreateAchieverPortIn;
import com.gomo.app.support.logging.AuditLog;
import com.gomo.app.support.logging.Timed;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class CreateMemberUseCase {

	private final VerifyJwtPortIn verifyJwtPortIn;
	private final EncodePasswordPortIn encodePasswordPortIn;
	private final CreatePointWalletPortIn createPointWalletPortIn;
	private final CreateAchieverPortIn createAchieverPortIn;
	private final MemberService memberService;
	private final MemberRepository memberRepository;

	@AuditLog(action = "CREATE_MEMBER")
	@Timed
	public UUID create(CreateMemberCommand command) {
		if (!verifyJwtPortIn.validateToken(command.temporaryToken())) {
			throw new IllegalArgumentException("Invalid temporary token");
		}

		Email email = Email.of(command.email());
		memberService.checkEmailDuplicated(email);

		Handle handle = Handle.of(command.handle());
		memberService.checkHandleDuplicated(handle);

		UUID memberId = UUIDGenerator.generate();
		Password verifiedPassword = LoginProvider.EMAIL.name().equals(command.loginProvider())
			? Password.ofRaw(command.rawPassword()) : Password.forOAuth(memberId.toString());
		Password encodedPassword = Password.ofEncoded(encodePasswordPortIn.encode(verifiedPassword.getPassword()));

		Member member = Member.of(
			memberId,
			email,
			encodedPassword,
			handle,
			MemberName.of(command.name()),
			Motto.of(command.motto()),
			LoginProvider.valueOf(command.loginProvider())
		);
		Member savedMember = memberRepository.save(member);
		createPointWalletPortIn.create(savedMember.getId());
		createAchieverPortIn.create(savedMember.getId());
		return savedMember.getId();
	}
}
