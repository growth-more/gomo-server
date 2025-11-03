package com.gomo.app.support.llm.application;

import com.gomo.app.common.arch.ApplicationService;

import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
class GeminiGenerateTextUseCase implements GenerateTextPortIn {

	private final LlmClientPortOut llmClientPortOut;

	@Override
	public GenerateTextDto generate(GenerateTextCommand command) {
		return llmClientPortOut.generate(command);
	}
}
