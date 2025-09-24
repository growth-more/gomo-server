package com.gomo.app.core.member.application.scheduler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gomo.app.core.interest.domain.model.RegistrantId;
import com.gomo.app.core.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.core.point.domain.model.TransactorId;
import com.gomo.app.core.point.domain.repository.PointRepository;
import com.gomo.app.core.point.domain.repository.PointWalletRepository;
import com.gomo.app.core.quest.domain.model.ParticipantId;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.core.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.core.streak.domain.model.AchieverId;
import com.gomo.app.core.streak.domain.repository.AchieverRepository;
import com.gomo.app.core.streak.domain.repository.StreakRepository;
import com.gomo.app.support.image.service.ImageService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberCleanupScheduler {

	private final ImageService imageService;
	private final PointRepository pointRepository;
	private final PointWalletRepository pointWalletRepository;
	private final AssignQuestRepository assignQuestRepository;
	private final RepeatQuestRepository repeatQuestRepository;
	private final InterestRelationRepository interestRelationRepository;
	private final MajorInterestRepository majorInterestRepository;
	private final InterestRepository interestRepository;
	private final StreakRepository streakRepository;
	private final AchieverRepository achieverRepository;
	private final MemberRepository memberRepository;

	@Value("${app.members.retentionDays}")
	private long retentionDays;

	@Scheduled(cron = "0 0 2 * * *")
	public void memberDataCleanUp() {
		List<Member> deletedMembers = memberRepository.findByDeletedAtBefore(LocalDateTime.now().minusDays(retentionDays));

		for (Member member : deletedMembers) {
			cleanupMemberDataAsync(member);
		}
	}

	@Async("memberCleanupExecutor")
	public CompletableFuture<Void> cleanupMemberDataAsync(Member member) {
		try {
			deleteMemberAndRelatedData(member);
			return CompletableFuture.completedFuture(null);
		} catch (Exception e) {
			return CompletableFuture.failedFuture(e);
		}
	}

	private void deleteMemberAndRelatedData(Member member) {

		// Quest 관련 데이터 삭제
		assignQuestRepository.deleteAllByParticipantId(ParticipantId.of(member.uuid()));
		repeatQuestRepository.deleteAllByParticipantId(ParticipantId.of(member.uuid()));

		// Interest 관련 데이터 삭제
		interestRelationRepository.deleteAllByRegistrantId(RegistrantId.of(member.uuid()));
		majorInterestRepository.deleteAllByRegistrantId(RegistrantId.of(member.uuid()));
		interestRepository.deleteAllByRegistrantId(RegistrantId.of(member.uuid()));

		// Point 관련 데이터 삭제
		pointRepository.deleteAllByTransactorId(TransactorId.of(member.uuid()));
		pointWalletRepository.deletePointWalletByTransactorId(TransactorId.of(member.uuid()));

		// Streak 관련 데이터 삭제
		streakRepository.deleteAllByAchieverId(AchieverId.of(member.uuid()));
		achieverRepository.deleteByAchieverId(AchieverId.of(member.uuid()));

		// 이미지 파일 삭제
		imageService.delete(member.getProfileImage().getUrl());
		imageService.delete(member.getProfileBanner().getUrl());

		// member 최종 삭제
		memberRepository.deleteById(member.getId());
	}
}
