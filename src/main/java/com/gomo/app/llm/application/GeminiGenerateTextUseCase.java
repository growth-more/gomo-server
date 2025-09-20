package com.gomo.app.llm.application;

import com.gomo.app.common.ApplicationService;

@ApplicationService
class GeminiGenerateTextUseCase implements GenerateTextUseCase {

	@Override
	public GenerateTextDto generate(GenerateTextCommand command) {
		// todo nurdy: 기존 py 기반 서빙 프로그램 마이그레이션 필요
		return null;
	}
}
