package com.gomo.app.member.application;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.presentation.request.UpdateHandleRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateHandleUseCase {

    private final MemberService memberService;

    public void update(UUID memberId, UpdateHandleRequest request) {
        Member member = memberService.find(MemberId.of(memberId));
        memberService.checkHandleDuplicated(Handle.of(request.getHandle()));
        member.updateHandle(request.getHandle());
    }
}
