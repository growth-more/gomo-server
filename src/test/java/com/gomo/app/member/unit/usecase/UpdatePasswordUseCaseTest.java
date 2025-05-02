package com.gomo.app.member.unit.usecase;

import com.gomo.app.member.application.UpdatePasswordUseCase;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.presentation.request.UpdatePasswordRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@DisplayName("[Application Unit]: 멤버 비밀번호 수정 기능 테스트")
@ExtendWith(SpringExtension.class)
public class UpdatePasswordUseCaseTest {

    @InjectMocks
    private UpdatePasswordUseCase sut;

    @Mock
    private PasswordService passwordService;

    @Mock
    private MemberService memberService;

    private static final String NEW_PASSWORD = "Update123!";
    private static final String ENCRYPT_PASSWORD = "Encrypt123!";
    private static final String PASSWORD = "Test123!";

    @DisplayName("비밀번호를 업데이트 한다.")
    @Test
    void update_member_password(){
        Member member = MemberFixture.member();
        UpdatePasswordRequest request = UpdatePasswordRequest.of(PASSWORD, NEW_PASSWORD);

        doReturn(member).when(memberService).find(MemberId.of(member.uuid()));
        doReturn(ENCRYPT_PASSWORD).when(passwordService).encode(NEW_PASSWORD);
        doReturn(true).when(passwordService).matches(PASSWORD, member.getPassword().getPassword());

        sut.update(member.getId().getId(), request);

        assertThat(member.getPassword().getPassword()).isEqualTo(ENCRYPT_PASSWORD);
    }

}
