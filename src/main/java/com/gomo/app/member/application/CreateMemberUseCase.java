package com.gomo.app.member.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.presentation.request.CreateMemberRequest;
import com.gomo.app.member.presentation.response.CreateMemberResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@ApplicationService
public class CreateMemberUseCase {

    private final MemberRepository memberRepository;
    private final PasswordService passwordService;

    public CreateMemberResponse create(CreateMemberRequest request) {
        UUID uuid = UUIDGenerator.generate();
        MemberId memberId = MemberId.of(uuid);
        Member member = request.toDomain(memberId, passwordService);
        Member savedMember = memberRepository.save(member);
        return CreateMemberResponse.of(savedMember.getId());
    }
}
