package com.gomo.app.member.unit.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.gomo.app.member.application.UpdateQuestPropertyUseCase;
import com.gomo.app.member.common.fixture.MemberFixture;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.QuestProperty;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.presentation.request.UpdateQuestPropertyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@DisplayName("[Application Unit]: 퀘스트 설정 수정 테스트")
@ExtendWith(MockitoExtension.class)
public class UpdateQuestPropertyUseCaseTest {
    @InjectMocks
    private UpdateQuestPropertyUseCase sut;

    @Mock
    private PasswordService passwordService;

    @Mock
    private MemberRepository memberRepository;

    private static final int DAILY_THRESHOLD = 1;
    private static final int WEEKLY_THRESHOLD = 3;
    private static final int MONTHLY_THRESHOLD = 5;

    @DisplayName("퀘스트 설정을 수정한다.")
    @Test
    void update_quest_property_successfully(){
        Member member = MemberFixture.member(passwordService);
        UpdateQuestPropertyRequest request = UpdateQuestPropertyRequest.of(DAILY_THRESHOLD, WEEKLY_THRESHOLD, MONTHLY_THRESHOLD);
        QuestProperty expected = request.toDomain();
        doReturn(Optional.of(member)).when(memberRepository).findById(member.getId());

        sut.update(member.getId(),request);

        QuestProperty actual = member.getQuestProperty();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
