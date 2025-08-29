package com.gomo.app.member.application;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.model.LoginProvider;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.presentation.request.CreateMemberRequest;
import com.gomo.app.member.presentation.response.CreateMemberResponse;
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

	public CreateMemberResponse create(CreateMemberRequest request) {
		memberService.checkEmailDuplicated(Email.of(request.getEmail()));
		memberService.checkHandleDuplicated(Handle.of(request.getHandle()));

		Member member = request.toDomain(MemberId.of(UUIDGenerator.generate()), LoginProvider.EMAIL, passwordService);
		Member savedMember = memberRepository.save(member);
		PointWallet pointWallet = PointWallet.createDefault(PointWalletId.of(UUIDGenerator.generate()),
			TransactorId.of(savedMember.uuid()));
		pointWalletRepository.save(pointWallet);
		achieverService.create(savedMember.uuid());

		return CreateMemberResponse.of(savedMember.getId());
	}
}
