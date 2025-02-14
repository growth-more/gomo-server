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
 * @ 총 일곱 가지 데이터가 존재한다.
 * @ 1. dilay not confirm quest
 * @ 2. daily confirm quest
 * @ 3. daily completed quest - java
 * @ 4. daily completed quest - spring
 * @ 5. daily participating quest - not confirm, not completed
 * @ 6. weekly participating quest - not confirm, not completed
 * @ 7. monthly participating quest - not confirm, not completed
 */
@Component
public class AssignQuestDataProvider {

	private static final String NOT_CONFIRMED_NOT_COMPLETED_JAVA_DAILY_QUEST_ID = "1450177f-d7ff-11ef-830c-233264c36b07";
	private static final String CONFIRMED_NOT_COMPLETED_SPRING_DAILY_QUEST_ID = "bf259c7a-d7ff-11ef-ac7f-3bd3057a2c2e";
	private static final String CONFIRMED_COMPLETED_JAVA_DAILY_QUEST_ID = "210891d5-d814-11ef-9cc5-cdb1eaaaac96";
	private static final String CONFIRMED_COMPLETED_SPRING_DAILY_QUEST_ID = "996604d8-d814-11ef-8d8d-fdccfa1ea3b3";
	private static final String PARTICIPATING_DAILY_QUEST_ID = "0194cbd7-8689-74ec-bd46-dc855f493c3b";
	private static final String PARTICIPATING_WEEKLY_QUEST_ID = "0194cbda-6135-79fc-b659-ebaac3684761";
	private static final String PARTICIPATING_MONTHLY_QUEST_ID = "0194cbeb-345e-74a6-9199-07bdb402ea36";
	private AssignQuest notConfirmed;
	private AssignQuest confirmed;
	private AssignQuest completedJava;
	private AssignQuest completedSpring;
	private AssignQuest dailyParticipatingQuest;
	private AssignQuest weeklyParticipatingQuest;
	private AssignQuest monthlyParticipatingQuest;

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

		dailyParticipatingQuest = assignQuestRepository.findById(AssignQuestId.of(UUID.fromString(PARTICIPATING_DAILY_QUEST_ID)))
			.orElseThrow(() -> new IllegalStateException("AssignQuestDataProvider 초기화 실패: 'dailyParticipatingQuest' 에 해당하는 AssignQuest가 없습니다."));

		weeklyParticipatingQuest = assignQuestRepository.findById(AssignQuestId.of(UUID.fromString(PARTICIPATING_WEEKLY_QUEST_ID)))
			.orElseThrow(() -> new IllegalStateException("AssignQuestDataProvider 초기화 실패: 'weeklyParticipatingQuest' 에 해당하는 AssignQuest가 없습니다."));

		monthlyParticipatingQuest = assignQuestRepository.findById(AssignQuestId.of(UUID.fromString(PARTICIPATING_MONTHLY_QUEST_ID)))
			.orElseThrow(() -> new IllegalStateException("AssignQuestDataProvider 초기화 실패: 'monthlyParticipatingQuest' 에 해당하는 AssignQuest가 없습니다."));
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

	public AssignQuest dailyParticipatingQuest() {
		return dailyParticipatingQuest;
	}

	public AssignQuest weeklyParticipatingQuest() {
		return weeklyParticipatingQuest;
	}

	public AssignQuest monthlyParticipatingQuest() {
		return monthlyParticipatingQuest;
	}
}
