package com.gomo.app.llm.application;

public interface GenerateTextUseCase {

	GenerateTextDto generate(GenerateTextCommand command);
}
