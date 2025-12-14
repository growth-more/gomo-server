package com.gomo.app.support.llm.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class PromptLoader {

	private final Map<String, String> promptCache = new ConcurrentHashMap<>();

	public String loadPrompt(String promptPath) {
		return promptCache.computeIfAbsent(promptPath, this::loadFromFile);
	}

	public String loadFromFile(String promptPath) {
		try {
			ClassPathResource resource = new ClassPathResource("prompts/" + promptPath);
			return Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load prompt: " + promptPath, e);
		}
	}
}
