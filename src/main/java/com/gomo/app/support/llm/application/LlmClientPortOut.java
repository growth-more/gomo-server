package com.gomo.app.support.llm.application;

import com.gomo.app.core.interest.application.port.dto.RegistrantDto;

public interface LlmClientPortOut {

	/**
	 * Retrieves the essential details of a single registrant by id.
	 *
	 * @param {@link GenerateTextCommand} The information to generate Quests with LLM.
	 * @return A {@link GenerateTextDto} containing the GeneratedText data.
	 */
	GenerateTextDto generate(GenerateTextCommand command);
}
