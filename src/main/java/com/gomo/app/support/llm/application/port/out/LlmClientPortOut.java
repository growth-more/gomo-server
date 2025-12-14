package com.gomo.app.support.llm.application.port.out;

import com.gomo.app.support.llm.application.port.dto.LlmRequest;
import com.gomo.app.support.llm.application.port.dto.LlmResponse;

/**
 * LLM Client Port (Driven Port)
 * Only
 */
public interface LlmClientPortOut {

	/**
	 * Generate Text from given Prompt
	 *
	 * @param {@link LlmRequest} The prompt for generate text.
	 * @return A {@link LlmResponse} LLM created raw text.
	 */
	LlmResponse generateText(LlmRequest request);
}
