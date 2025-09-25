package com.gomo.app.core.member.application.usecase;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.jwt.port.VerifyJwtPortIn;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.member.application.port.EncodePasswordPortOut;
import com.gomo.app.core.member.application.port.command.CreateMemberCommand;
import com.gomo.app.core.member.application.port.dto.CreateMemberDto;
import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.domain.model.Handle;
import com.gomo.app.core.member.domain.model.LoginProvider;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.model.MemberId;
import com.gomo.app.core.member.domain.model.MemberName;
import com.gomo.app.core.member.domain.model.Motto;
import com.gomo.app.core.member.domain.model.Password;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.core.point.domain.model.PointWallet;
import com.gomo.app.core.point.domain.model.PointWalletId;
import com.gomo.app.core.point.domain.model.TransactorId;
import com.gomo.app.core.point.domain.repository.PointWalletRepository;
import com.gomo.app.core.streak.domain.service.AchieverService;
import com.gomo.app.support.logging.AuditLog;
import com.gomo.app.support.logging.Timed;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class CreateMemberUseCase {

	private final VerifyJwtPortIn verifyJwtPortIn;
	private final MemberService memberService;
	private final EncodePasswordPortOut encodePasswordPortOut;
	private final MemberRepository memberRepository;
	private final PointWalletRepository pointWalletRepository;
	private final AchieverService achieverService;

	@AuditLog(action = "CREATE_MEMBER")
	@Timed
	public CreateMemberDto create(CreateMemberCommand command) {
		verifyJwtPortIn.validateToken(command.temporaryToken());

		Email email = Email.of(command.email());
		memberService.checkEmailDuplicated(email);

		Handle handle = Handle.of(command.handle());
		memberService.checkHandleDuplicated(handle);

		MemberId memberId = MemberId.of(UUIDGenerator.generate());
		Password verifiedPassword = LoginProvider.EMAIL.name().equals(command.loginProvider())
			? Password.ofRaw(command.rawPassword()) : Password.forOAuth(memberId.toString());
		Password encodedPassword = Password.ofEncoded(encodePasswordPortOut.encode(verifiedPassword.getPassword()));

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
