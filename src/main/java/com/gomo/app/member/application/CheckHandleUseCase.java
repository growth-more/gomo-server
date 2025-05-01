package com.gomo.app.member.application;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.service.MemberService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CheckHandleUseCase {

    private final MemberService memberService;

    public void checkHandleDuplicated(String handle) {
        memberService.checkHandleDuplicated(Handle.of(handle));
    }
}
