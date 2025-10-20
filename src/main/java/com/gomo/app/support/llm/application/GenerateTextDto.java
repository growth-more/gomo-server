package com.gomo.app.support.llm.application;

import java.util.List;
import java.util.Map;

public record GenerateTextDto(Map<String, List<String>> generatedText) {}

