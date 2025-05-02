package com.gomo.app.member.application;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.presentation.request.UpdatePasswordRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdatePasswordUseCase {

    private final MemberService memberService;
    private final PasswordService passwordService;

    public void update(UUID memberId, UpdatePasswordRequest request) {
        Member member = memberService.find(MemberId.of(memberId));
        member.updatePassword(request.getOriginPassword(), request.getUpdatedPassword(), passwordService);

    }
}
