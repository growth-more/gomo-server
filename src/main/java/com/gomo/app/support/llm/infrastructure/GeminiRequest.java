package com.gomo.app.support.llm.infrastructure;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.gomo.app.core.quest.domain.model.quest.QuestType;

public record GeminiRequest(
	String model,
	String reasoning_effort,
	List<Prompt> messages
) {
	private static Prompt createSystemPrompt(){
		return new Prompt("system", """
            당신은 개인 맞춤형 도전 과제를 생성하는 전문가 입니다. 아래 가이드라인을 준수하여 도전 과제를 생성하세요.

            **도전 과제 생성 가이드라인**
            #1. 아래 주어지는 ‘개인 정보’를 바탕으로 ‘도전 과제’를 생성합니다.
            #1-1. ‘개인 정보’의 숙련도와 퀘스트 타입으로 ‘도전 과제’의 ‘소요 시간(분)’을 결정합니다.
            #1-1-1. 숙련도는 0 - 100 사이의 정수입니다.
            #1-1-2. 퀘스트 타입은 각각 Daily(30 - 90), Weekly(300 - 900), Monthly(1000 - 1500) 사이의 무작위 정수입니다.
            #1-1-3. ‘도전 과제’의 ‘소요 시간(분)’은 연산식(숙련도 * 0.1 + 퀘스트 타입) 결과로 0 - 1510 사이의 정수입니다.
            #1-2. ‘도전 과제’의 content는 ‘개인 정보’의 관심사와 연관된 목표입니다.
            #1-2-1. content는 명확하고 구조적인 행동을 제시합니다.
            #1-2-1-1. 문장 형식은 “진행합니다.” 가 아닌 “진행”과 같이 짧게 끝맺음한다.
            #1-2-2. content는 실질적으로 ‘소요 시간(분)’ 이내에 달성 가능해야 합니다.
            #1-2-2-1. 30 - 100 ex) “알고리즘 1문제 풀이”, “TIL 작성”, “기술 블로그 하나 정독”
            #1-2-2-2. 300 - 910 ex) “기술 블로그 하나 작성”, “개인 프로젝트 pr 하나 작업”, “개인 프로젝트 로깅 시스템 점검”
            #1-2-2-3. 1000 - 1510 ex) “서킷 브레이커 학습 후, 개인 프로젝트에 적용하기”, “오픈 소스 1회 기여하기”, “멀티 모듈 학습하고 토이 프로젝트 진행하기”
            #2. ‘도전 과제’의 응답 형식은 반드시 JSON으로 작성해주세요.
            #2-1. 생성된 도전과제는 30자 이내의 동사형으로 제시해주세요.
            #2-2. 응답 형식 예시: {
            	["관심사명" : [{ “content” : “[생성된 도전 과제]” }, ...],]}
            #2-3. 이전 수행 퀘스트를 참고하여, 중복이 발생하지 않도록 제시해주세요.
            #2-4. 각 관심사 별 퀘스트 수를 동일한 비율로 생성해 주세요.
        """);
	}

	private static Prompt createUserPrompt(Map<String, Long> interests, QuestType questType, int amount){
		String interestsText = interests.entrySet().stream()
			.map(entry -> entry.getKey() + "(숙련도 : " + entry.getValue() + ")")
			.collect(Collectors.joining(", "));

		String promptText = String.format(
			"""
			**개인 정보**
				- 관심사: %s
				- 퀘스트 타입: %s
				- 퀘스트 수: %s
			""", interestsText, questType.name(), amount
		);

		return new Prompt("user", promptText);
	}

	public static GeminiRequest createPrompt(Map<String, Long> interests, QuestType questType, int amount){
		Prompt system = createSystemPrompt();
		Prompt user = createUserPrompt(interests, questType, amount);
		return new GeminiRequest("gemini-2.5-flash", "none", List.of(system, user));
	}

	public record Prompt(String role, String content){}
}