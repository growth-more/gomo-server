package com.gomo.app.support.llm.infrastructure;

import java.util.List;

public record GeminiResponse(
	List<Choice> choices,
	int created,
	String id,
	String model,
	String object,
	GeminiUsage usage
) {
	public record Choice(String finish_reason, int index, Message message){}
	public record Message(String content, String role){}
	public record GeminiUsage(int completion_tokens, int prompt_tokens, int total_tokens){}
}


