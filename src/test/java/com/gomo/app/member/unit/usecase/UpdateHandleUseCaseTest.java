package com.gomo.app.member.unit.usecase;

import com.gomo.app.member.application.UpdateHandleUseCase;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.presentation.request.UpdateHandleRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@DisplayName("[Application Unit]: 멤버 핸들 수정 기능 테스트")
@ExtendWith(MockitoExtension.class)
public class UpdateHandleUseCaseTest {

    @InjectMocks
    private UpdateHandleUseCase sut;

    @Mock
    private MemberService memberService;

    private static final String NEW_HANDLE = "@update_handle";

    @DisplayName("멤버 핸들을 업데이트 한다.")
    @Test
    void update_member_handle(){
        Member member = MemberFixture.member();
        UpdateHandleRequest request = UpdateHandleRequest.of(NEW_HANDLE);

        doReturn(member).when(memberService).find(MemberId.of(member.uuid()));
        doNothing().when(memberService).checkHandleDuplicated(Handle.of(request.getHandle()));

        sut.update(member.uuid(), request);

        assertThat(member.getHandle().getHandle()).isEqualTo(NEW_HANDLE);
    }
}
