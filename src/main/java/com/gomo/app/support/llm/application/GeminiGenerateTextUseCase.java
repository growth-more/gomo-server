package com.gomo.app.support.llm.application;

import com.gomo.app.common.ApplicationService;

@ApplicationService
class GeminiGenerateTextUseCase implements GenerateTextPortIn {

	@Override
	public GenerateTextDto generate(GenerateTextCommand command) {
		// todo nurdy: 기존 py 기반 llm 호출 로직 마이그레이션 필요
		return null;
	}
}
