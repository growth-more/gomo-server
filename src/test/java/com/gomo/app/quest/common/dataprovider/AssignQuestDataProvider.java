package com.gomo.app.quest.common.dataprovider;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;

import jakarta.annotation.PostConstruct;

/**
 * 실제 데이터베이스에 존재하는 할당 퀘스트 데이터를 제공한다.
 * @ 총 네 가지 데이터가 존재한다.
 * @ 1. not confirm, not completed java daily quest
 * @ 2. confirm, not completed spring daily quest
 * @ 3. confirm, completed java daily quest
 * @ 4. confirm, completed spring daily quest
 */
@Component
public class AssignQuestDataProvider {

	private static final String NOT_CONFIRMED_NOT_COMPLETED_JAVA_DAILY_QUEST_ID = "1450177f-d7ff-11ef-830c-233264c36b07";
	private static final String CONFIRMED_NOT_COMPLETED_SPRING_DAILY_QUEST_ID = "bf259c7a-d7ff-11ef-ac7f-3bd3057a2c2e";
	private static final String CONFIRMED_COMPLETED_JAVA_DAILY_QUEST_ID = "210891d5-d814-11ef-9cc5-cdb1eaaaac96";
	private static final String CONFIRMED_COMPLETED_SPRING_DAILY_QUEST_ID = "996604d8-d814-11ef-8d8d-fdccfa1ea3b3";
	private AssignQuest notConfirmed;
	private AssignQuest confirmed;
	private AssignQuest completedJava;
	private AssignQuest completedSpring;

	@Autowired
	private AssignQuestRepository assignQuestRepository;

	@PostConstruct
	public void initialize() {
		notConfirmed = assignQuestRepository.findById(AssignQuestId.of(UUID.fromString(NOT_CONFIRMED_NOT_COMPLETED_JAVA_DAILY_QUEST_ID)))
			.orElseThrow(() -> new IllegalStateException("AssignQuestDataProvider 초기화 실패: 'notConfirmed' 에 해당하는 AssignQuest가 없습니다."));

		confirmed = assignQuestRepository.findById(AssignQuestId.of(UUID.fromString(CONFIRMED_NOT_COMPLETED_SPRING_DAILY_QUEST_ID)))
			.orElseThrow(() -> new IllegalStateException("AssignQuestDataProvider 초기화 실패: 'confirmed' 에 해당하는 AssignQuest가 없습니다."));

		completedJava = assignQuestRepository.findById(AssignQuestId.of(UUID.fromString(CONFIRMED_COMPLETED_JAVA_DAILY_QUEST_ID)))
			.orElseThrow(() -> new IllegalStateException("AssignQuestDataProvider 초기화 실패: 'completedJava' 에 해당하는 AssignQuest가 없습니다."));

		completedSpring = assignQuestRepository.findById(AssignQuestId.of(UUID.fromString(CONFIRMED_COMPLETED_SPRING_DAILY_QUEST_ID)))
			.orElseThrow(() -> new IllegalStateException("AssignQuestDataProvider 초기화 실패: 'completedSpring' 에 해당하는 AssignQuest가 없습니다."));
	}

	public AssignQuest notConfirmed() {
		return notConfirmed;
	}

	public AssignQuest confirmed() {
		return confirmed;
	}

	public AssignQuest completedJava() {
		return completedJava;
	}

	public AssignQuest completedSpring() {
		return completedSpring;
	}
}
