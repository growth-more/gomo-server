package com.gomo.app.survey.unit.usecase;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.survey.application.CreateSurveyResultCommand;
import com.gomo.app.survey.application.CreateSurveyResultUseCase;
import com.gomo.app.survey.application.SurveyItemDto;
import com.gomo.app.survey.domain.repository.SurveyResultRepository;

@DisplayName("[Application unit]: 설문 결과 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class CreateSurveyResultUseCaseTest {

	@InjectMocks
	private CreateSurveyResultUseCase sut;

	@Mock
	private SurveyResultRepository surveyResultRepository;

	@DisplayName("회원의 설문 결과를 생성한다.")
	@Test
	void create_survey_result() {
		sut.create(CreateSurveyResultCommand.of(UUID.randomUUID(), getSurveyItems()));
		verify(surveyResultRepository, times(1)).saveAll(anyList());
	}

	private static @NotNull List<SurveyItemDto> getSurveyItems() {
		return List.of(
			SurveyItemDto.withCustomAnswer(UUID.randomUUID(), UUID.randomUUID(), "survey item content", null, null),
			SurveyItemDto.withCustomAnswer(UUID.randomUUID(), UUID.randomUUID(), "기타", null, "custom answer")
		);
	}
}
