package com.gomo.app.member.unit.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.domain.service.ReadMemberService;
import com.gomo.app.member.exception.MemberNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Domain unit]: 회원 엔티티 조회 테스트")
public class ReadMemberServiceTest {

    @InjectMocks
    ReadMemberService sut;

    @Mock
    MemberRepository memberRepository;

    @DisplayName("회원 엔티티를 조회한다.")
    @Test
    void find_member(){
        doReturn(Optional.of(mock(Member.class))).when(memberRepository).findById(any(MemberId.class));

        Member actual = sut.find(UUID.randomUUID());

        assertThat(actual).isNotNull();
    }

    @DisplayName("존재하지 않는 회원 엔티티를 조회한다.")
    @Test
    void find_nonexistent_member(){
        doReturn(Optional.empty()).when(memberRepository).findById(any(MemberId.class));

        assertThatThrownBy(() -> sut.find(UUID.randomUUID()))
            .isInstanceOf(MemberNotFoundException.class);
    }
}
