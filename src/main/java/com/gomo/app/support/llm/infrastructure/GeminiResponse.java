package com.gomo.app.support.llm.infrastructure;

import java.util.List;

/**
 *
 * @param choices: a list of generated contents. normally returns 1 content.
 * @param created: response created time. (Unix timestamp, unit: s)
 * @param id: Unique ID.
 * @param model: Gemini Model name who create response( normally: gemini-2.5-flash )
 * @param object: API response object specification string( normally: "chat.completion")
 * @param usage: Total Usage.
 */
public record GeminiResponse(
	List<Choice> choices,
	int created,
	String id,
	String model,
	String object,
	GeminiUsage usage
) {
	/**
	 *
	 * @param finish_reason: reason which stopped model( ex: "stop", "length")
	 * @param index: index of Choice array.
	 * @param message: information of generated model (response and role)
	 */
	public record Choice(String finish_reason, int index, Message message) {
	}

	/**
	 *
	 * @param content: generated text contents(in this project. generated quest)
	 * @param role: role ("user", "assistant")
	 */
	public record Message(String content, String role) {
	}

	/**
	 *
	 * @param completion_tokens : Total usage to Response Token.
	 * @param prompt_tokens : Total usage to Prompt Token.
	 * @param total_tokens : Total usage of Token.
	 */
	public record GeminiUsage(int completion_tokens, int prompt_tokens, int total_tokens) {
	}
}


