package com.gomo.app.member.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.member.application.UpdateMemberUseCase;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.presentation.request.UpdateHandleRequest;
import com.gomo.app.member.presentation.request.UpdateMemberRequest;
import com.gomo.app.member.presentation.request.UpdatePasswordRequest;

@DisplayName("[Application Unit]: 멤버 수정 기능 테스트")
@ExtendWith(MockitoExtension.class)
public class UpdateMemberUseCaseTest {

    @InjectMocks
    private UpdateMemberUseCase sut;

    @Mock
    private MemberService memberService;

    private static final String NEW_NAME = "NewName";
    private static final String NEW_MOTTO = "NewMotto";


    @DisplayName("회원 정보(모토, 이름)를 수정한다.")
    @Test
    void update_member_name_and_motto(){
        Member member = MemberFixture.member();
        UpdateMemberRequest request = UpdateMemberRequest.of(NEW_NAME, NEW_MOTTO);

        doReturn(member).when(memberService).find(MemberId.of(member.uuid()));

        sut.update(member.getId().getId(), request);

        assertThat(member.getName().getName()).isEqualTo(NEW_NAME);
        assertThat(member.getMotto().getMotto()).isEqualTo(NEW_MOTTO);
    }

}
