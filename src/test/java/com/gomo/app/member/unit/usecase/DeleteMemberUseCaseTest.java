package com.gomo.app.member.unit.usecase;

import com.gomo.app.member.application.DeleteMemberUseCase;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.ActivateStatus;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.domain.service.PasswordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@DisplayName("[Application Unit]: 멤버 삭제 테스트")
@ExtendWith(MockitoExtension.class)
public class DeleteMemberUseCaseTest {

    @InjectMocks
    private DeleteMemberUseCase sut;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordService passwordService;

    @DisplayName("멤버 삭제 테스트")
    @Test
    void delete_member_successful() {
        Member member = MemberFixture.member(passwordService);
        doReturn(Optional.of(member)).when(memberRepository).findById(member.getId());

        sut.delete(member.getId());

        assertThat(member.getActivateStatus()).isEqualTo(ActivateStatus.DELETED);
    }
}
