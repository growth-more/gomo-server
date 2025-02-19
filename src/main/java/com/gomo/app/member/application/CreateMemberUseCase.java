package com.gomo.app.member.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.presentation.request.CreateMemberRequest;
import com.gomo.app.member.presentation.response.CreateMemberResponse;
import com.gomo.app.point.domain.model.PointWallet;
import com.gomo.app.point.domain.model.PointWalletId;
import com.gomo.app.point.domain.model.TransactorId;
import com.gomo.app.point.domain.repository.PointWalletRepository;
import com.gomo.app.point.domain.service.PointWalletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@ApplicationService
public class CreateMemberUseCase {

    private final MemberRepository memberRepository;
    private final PointWalletRepository pointWalletRepository;
    private final PasswordService passwordService;

    public CreateMemberResponse create(CreateMemberRequest request) {
        UUID member_uuid = UUIDGenerator.generate();
        MemberId memberId = MemberId.of(member_uuid);
        Member member = request.toDomain(memberId, passwordService);
        Member savedMember = memberRepository.save(member);

        UUID pointwallet_uuid = UUIDGenerator.generate();
        PointWalletId pointWalletId = PointWalletId.of(pointwallet_uuid);
        PointWallet pointWallet = PointWallet.createDefault(pointWalletId, TransactorId.of(member.getId().getId()));


        return CreateMemberResponse.of(savedMember.getId());
    }
}
