package com.gomo.app.support.llm.application.port.dto;

import java.util.List;
import java.util.Map;

public record GenerateTextDto(Map<String, List<String>> questsByType) {
	public static GenerateTextDto of(Map<String, List<String>> questsByType) {
		return new GenerateTextDto(questsByType);
	}
}

