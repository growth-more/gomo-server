package com.gomo.app.member.application;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.logging.AuditLog;
import com.gomo.app.logging.Timed;
import com.gomo.app.member.application.port.command.CreateMemberCommand;
import com.gomo.app.member.application.port.dto.CreateMemberDto;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.model.LoginProvider;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.model.MemberName;
import com.gomo.app.member.domain.model.Motto;
import com.gomo.app.member.domain.model.Password;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.point.domain.model.PointWallet;
import com.gomo.app.point.domain.model.PointWalletId;
import com.gomo.app.point.domain.model.TransactorId;
import com.gomo.app.point.domain.repository.PointWalletRepository;
import com.gomo.app.streak.domain.service.AchieverService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class CreateMemberUseCase {

	private final MemberService memberService;
	private final PasswordService passwordService;
	private final MemberRepository memberRepository;
	private final PointWalletRepository pointWalletRepository;
	private final AchieverService achieverService;

	@AuditLog(action = "CREATE_MEMBER")
	@Timed
	public CreateMemberDto create(CreateMemberCommand command) {
		Email email = Email.of(command.email());
		memberService.checkEmailDuplicated(email);

		Handle handle = Handle.of(command.handle());
		memberService.checkHandleDuplicated(handle);

		MemberId memberId = MemberId.of(UUIDGenerator.generate());
		Password verifiedPassword = LoginProvider.EMAIL.name().equals(command.loginProvider())
			? Password.ofRaw(command.rawPassword()) : Password.forOAuth(memberId.toString());
		Password encodedPassword = verifiedPassword.encodedWith(passwordService);

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
		PointWallet pointWallet = PointWallet.createDefault(PointWalletId.of(UUIDGenerator.generate()), TransactorId.of(savedMember.uuid()));
		pointWalletRepository.save(pointWallet);
		achieverService.create(savedMember.uuid());
		return CreateMemberDto.of(savedMember.uuid());
	}
}
