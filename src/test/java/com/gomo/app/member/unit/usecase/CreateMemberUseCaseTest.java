package com.gomo.app.member.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.member.application.CreateMemberUseCase;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.presentation.request.CreateMemberRequest;
import com.gomo.app.member.presentation.response.CreateMemberResponse;

@DisplayName("[Application Unit]: 멤버 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class CreateMemberUseCaseTest {

    @InjectMocks
    private CreateMemberUseCase sut;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordService passwordService;

    @BeforeEach
    void setUp() {
        when(passwordService.encode("Test123!")).thenReturn("Encode123!");
    }

    // TODO <jhl221123 to nurdykim>: 회원 생성 로직 수정 후, 테스트 수정이 안되었던 것 같습니다.
    @DisplayName("회원을 등록한다.")
    @Test
    void create_member(){
        Member member = MemberFixture.member(passwordService);
        CreateMemberResponse expected = CreateMemberResponse.of(member.getId());

        doReturn(member).when(memberRepository).save(any(Member.class));

        CreateMemberResponse actual = sut.create(
                CreateMemberRequest.of(
                        member.getEmail().getEmail(),
                        member.getPassword().getPassword(),
                        member.getHandle().getHandle(),
                        member.getName().getName(),
                        member.getMotto().getMotto()
                )
        );

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
