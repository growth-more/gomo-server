package com.gomo.app.common.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class PromptLoader {
	public String loadPrompt(String promptPath) {
		try {
			ClassPathResource resource = new ClassPathResource("prompts/"+promptPath);
			return Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load prompt: "+promptPath, e);
		}
	}
}
