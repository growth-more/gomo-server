package com.gomo.app.survey.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.survey.application.ReadSurveyQuestionUseCase;
import com.gomo.app.survey.application.SurveyQuestionDto;
import com.gomo.app.survey.domain.repository.SurveyItemRepository;
import com.gomo.app.survey.domain.repository.SurveyQuestionRepository;
import com.gomo.app.survey.fixture.SurveyItemFixture;
import com.gomo.app.survey.fixture.SurveyQuestionFixture;

@DisplayName("[Application unit]: 설문 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class ReadSurveyResultUseCaseTest {

	@InjectMocks
	private ReadSurveyQuestionUseCase sut;

	@Mock
	private SurveyQuestionRepository surveyQuestionRepository;

	@Mock
	private SurveyItemRepository surveyItemRepository;

	@DisplayName("설문 목록을 조회한다.")
	@Test
	void find_survey() {
		doReturn(List.of(SurveyQuestionFixture.surveyQuestion())).when(surveyQuestionRepository).findAll();
		doReturn(List.of(SurveyItemFixture.surveyItem(), SurveyItemFixture.surveyItem())).when(surveyItemRepository).findAllBySurveyQuestionId(any());
		List<SurveyQuestionDto> expected = sut.findAll();
		assertThat(expected).hasSize(1);
		assertThat(expected.get(0).surveyItems()).hasSize(2);
	}
}
