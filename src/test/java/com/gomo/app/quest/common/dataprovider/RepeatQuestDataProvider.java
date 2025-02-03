package com.gomo.app.quest.common.dataprovider;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.model.RepeatQuestId;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;

import jakarta.annotation.PostConstruct;

/**
 * 실제 데이터베이스에 존재하는 할당 퀘스트 데이터를 제공한다.
 * @ java daily, spring daily 총 두 가지 데이터가 존재한다.
 */
@Component
public class RepeatQuestDataProvider {

	private static final String JAVA_DAILY_ID = "3892ced2-d816-11ef-a05f-25caca7d3e8c";
	private static final String SPRING_DAILY_ID = "a49f544f-d816-11ef-969c-6f84f91c1c7d";
	private RepeatQuest java;
	private RepeatQuest spring;

	@Autowired
	private RepeatQuestRepository repeatQuestRepository;

	@PostConstruct
	public void initialize() {
		java = repeatQuestRepository.findById(RepeatQuestId.of(UUID.fromString(JAVA_DAILY_ID)))
			.orElseThrow(() -> new IllegalStateException("RepeatQuestDataProvider 초기화 실패: JAVA_DAILY_ID에 해당하는 RepeatQuest가 없습니다."));

		spring = repeatQuestRepository.findById(RepeatQuestId.of(UUID.fromString(SPRING_DAILY_ID)))
			.orElseThrow(() -> new IllegalStateException("RepeatQuestDataProvider 초기화 실패: SPRING_DAILY_ID에 해당하는 RepeatQuest가 없습니다."));
	}

	public RepeatQuest java() {
		return java;
	}

	public RepeatQuest spring() {
		return spring;
	}
}
