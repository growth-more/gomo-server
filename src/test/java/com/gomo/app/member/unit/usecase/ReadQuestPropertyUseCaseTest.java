package com.gomo.app.member.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gomo.app.member.application.ReadQuestPropertyUseCase;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.presentation.response.ReadQuestPropertyResponse;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@DisplayName("[Application Unit]: 퀘스트 설정 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class ReadQuestPropertyUseCaseTest {

    @InjectMocks
    private ReadQuestPropertyUseCase sut;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordService passwordService;

    private static final int DAILY_THRESHOLD = 0;
    private static final int WEEKLY_THRESHOLD = 0;
    private static final int MONTHLY_THRESHOLD = 0;

    @DisplayName("회원의 퀘스트 설정 정보를 조회한다.")
    @Test
    void find_quest_property_successfully(){

        Member member = MemberFixture.member(passwordService);
        ReadQuestPropertyResponse expected = ReadQuestPropertyResponse.of(DAILY_THRESHOLD, WEEKLY_THRESHOLD, MONTHLY_THRESHOLD);

        doReturn(Optional.of(member)).when(memberRepository).findById(member.getId());

        ReadQuestPropertyResponse actual = sut.find(member.getId());

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
