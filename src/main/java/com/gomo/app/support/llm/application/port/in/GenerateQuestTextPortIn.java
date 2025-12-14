package com.gomo.app.support.llm.application.port.in;

import com.gomo.app.support.llm.application.port.dto.GenerateTextDto;

/**
 * Quest generate Port (Driving port)
 * For generate LLM Quest text for Quest Domain
 */
public interface GenerateQuestTextPortIn {
	GenerateTextDto generate(GenerateQuestTextCommand command);
}
