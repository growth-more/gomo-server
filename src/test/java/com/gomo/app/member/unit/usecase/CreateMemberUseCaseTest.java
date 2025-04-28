package com.gomo.app.member.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.member.application.CreateMemberUseCase;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.presentation.request.CreateMemberRequest;
import com.gomo.app.member.presentation.response.CreateMemberResponse;
import com.gomo.app.point.domain.model.PointWallet;
import com.gomo.app.point.domain.model.PointWalletId;
import com.gomo.app.point.domain.model.TransactorId;
import com.gomo.app.point.domain.repository.PointWalletRepository;

@DisplayName("[Application Unit]: 멤버 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class CreateMemberUseCaseTest {

    @InjectMocks
    private CreateMemberUseCase sut;

    @Mock
    private MemberService memberService;

    @Mock
    private PasswordService passwordService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PointWalletRepository pointWalletRepository;

    @DisplayName("회원을 등록한다.")
    @Test
    void create_member(){
        Member member = MemberFixture.member();
        UUID pointwallet_uuid = UUIDGenerator.generate();
        PointWallet pointWallet = PointWallet.createDefault(PointWalletId.of(pointwallet_uuid), TransactorId.of(member.getId().getId()));
        CreateMemberResponse expected = CreateMemberResponse.of(member.getId());

        doNothing().when(memberService).checkEmailDuplicated(any());
        doNothing().when(memberService).checkHandleDuplicated(any());
        doReturn("Encode123!").when(passwordService).encode(member.getPassword().getPassword());
        doReturn(member).when(memberRepository).save(any(Member.class));
        doReturn(pointWallet).when(pointWalletRepository).save(any(PointWallet.class));

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
