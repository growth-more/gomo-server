package com.gomo.app.support.llm.application;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.support.llm.infrastructure.GeminiApiAdapter;

import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
class GeminiGenerateTextUseCase implements GenerateTextPortIn {

	private final GeminiApiAdapter geminiApiAdapter;

	@Override
	public GenerateTextDto generate(GenerateTextCommand command) {
		return geminiApiAdapter.generate(command);
	}
}
