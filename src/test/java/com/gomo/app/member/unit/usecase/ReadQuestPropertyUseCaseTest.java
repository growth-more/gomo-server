package com.gomo.app.member.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.member.application.ReadQuestPropertyUseCase;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.model.QuestProperty;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.presentation.response.ReadQuestPropertyResponse;

@DisplayName("[Application Unit]: 퀘스트 설정 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class ReadQuestPropertyUseCaseTest {

    @InjectMocks
    private ReadQuestPropertyUseCase sut;

    @Mock
    private MemberService memberService;

    @DisplayName("회원의 퀘스트 설정 정보를 조회한다.")
    @Test
    void find_quest_property_successfully(){
        Member member = MemberFixture.member();
        QuestProperty request = QuestProperty.createDefault();
        ReadQuestPropertyResponse expected = ReadQuestPropertyResponse.of(request);

        doReturn(member).when(memberService).find(MemberId.of(member.uuid()));

        ReadQuestPropertyResponse actual = sut.find(member.uuid());

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
