package com.gomo.app.quest.integration;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.interest.common.dataprovider.InterestDataProvider;
import com.gomo.app.interest.common.util.InterestDataHelper;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.point.common.dataprovider.PointWalletDataProvider;
import com.gomo.app.point.domain.model.PointWallet;
import com.gomo.app.point.domain.repository.PointWalletRepository;
import com.gomo.app.quest.application.CompleteAssignQuestUseCase;
import com.gomo.app.quest.common.dataprovider.AssignQuestDataProvider;
import com.gomo.app.quest.common.util.PointDataHelper;
import com.gomo.app.quest.common.util.StreakDataHelper;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.presentation.request.CompleteAssignQuestRequest;
import com.gomo.app.streak.domain.model.Streak;
import com.gomo.app.streak.domain.repository.StreakRepository;

@DisplayName("[Application integration]: 퀘스트 완료 및 이벤트 처리 테스트")
public class CompleteAssignQuestUseCaseTest extends IntegrationTestBase {

	@Autowired
	CompleteAssignQuestUseCase sut;

	@Autowired
	InterestRepository interestRepository;

	@Autowired
	PointWalletRepository pointWalletRepository;

	@Autowired
	StreakRepository streakRepository;

	@Autowired
	AssignQuestDataProvider assignQuestDataProvider;
	AssignQuest confirmed;

	@Autowired
	InterestDataProvider interestDataProvider;
	Interest interest;

	@Autowired
	PointWalletDataProvider pointWalletDataProvider;
	PointWallet pointWallet;

	@Autowired
	InterestDataHelper interestDataHelper;

	@Autowired
	PointDataHelper pointDataHelper;

	@Autowired
	StreakDataHelper streakDataHelper;

	@BeforeEach
	void setUp() {
		confirmed = assignQuestDataProvider.confirmed();
		interest = interestDataProvider.spring();
		pointWallet = pointWalletDataProvider.pointWallet();
	}

	@AfterEach
	void tearDown() {
		interestDataHelper.cleanUp();
		pointDataHelper.cleanUp();
		streakDataHelper.cleanUp();
	}

	@DisplayName("퀘스트를 완료하면, 포인트와 숙련도 점수가 오르고 스트릭이 생성된다.")
	@Test
	void complete_quest() {
		sut.complete(
			confirmed.getQuest().getParticipantId().getId(),
			confirmed.getId(),
			CompleteAssignQuestRequest.of("https://proof")
		);

		assertThatProficiencyEnhanced();
		assertThatPointIncreased();
		assertThatStreakAdded();
	}

	private void assertThatStreakAdded() {
		int streakIncrement = 1;
		List<Streak> streaks = streakRepository.findAll();
		assertThat(streaks.size()).isEqualTo(3 + streakIncrement);
	}

	private void assertThatPointIncreased() {
		int pointIncrement = 10;
		PointWallet enhancedPointWallet = pointWalletRepository.findById(pointWallet.getId()).get();
		assertThat(enhancedPointWallet.getBalance().getAmount()).isEqualTo(pointWallet.getBalance().getAmount() + pointIncrement);
	}

	private void assertThatProficiencyEnhanced() {
		int scoreIncrement = 2;
		Interest enhancedInterest = interestRepository.findById(interest.getId()).get();
		assertThat(enhancedInterest.getProficiency().getTotalScore()).isEqualTo(interest.getProficiency().getTotalScore() + scoreIncrement);
	}
}
