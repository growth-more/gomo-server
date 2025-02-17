package com.gomo.app.member.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gomo.app.member.application.ReadMemberUseCase;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.presentation.response.ReadMemberResponse;
import com.gomo.app.point.domain.model.Balance;
import com.gomo.app.point.domain.model.TransactorId;
import com.gomo.app.point.domain.service.PointWalletService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@DisplayName("[Application Unit]: 멤버 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class ReadMemberUseCaseTest {
    @InjectMocks
    private ReadMemberUseCase sut;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PointWalletService pointWalletService;

    @Mock
    private PasswordService passwordService;

    private static final int BALANCE_AMOUNT = 5000;

    @DisplayName("멤버 조회에 성공한다.")
    @Test
    void read_member_successfully(){
        Member member = MemberFixture.member(passwordService);
        Balance balance = Balance.of(BALANCE_AMOUNT);
        ReadMemberResponse expected = ReadMemberResponse.of(member, BALANCE_AMOUNT);

        doReturn(Optional.of(member)).when(memberRepository).findById(member.getId());
        doReturn(balance).when(pointWalletService).findBalance(TransactorId.of(member.getId().getId()));

        ReadMemberResponse actual = sut.find(member.getId());

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

    }
}
