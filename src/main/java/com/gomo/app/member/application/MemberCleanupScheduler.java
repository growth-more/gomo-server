package com.gomo.app.member.application;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gomo.app.image.ImageService;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.service.InterestRelationService;
import com.gomo.app.interest.domain.service.InterestService;
import com.gomo.app.interest.domain.service.MajorInterestService;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.point.domain.model.TransactorId;
import com.gomo.app.point.domain.service.PointService;
import com.gomo.app.point.domain.service.PointWalletService;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.service.AssignQuestService;
import com.gomo.app.quest.domain.service.RepeatQuestService;
import com.gomo.app.streak.domain.model.AchieverId;
import com.gomo.app.streak.domain.service.AchieverService;
import com.gomo.app.streak.domain.service.StreakService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberCleanupScheduler {

	private final MemberService memberService;
	private final ImageService imageService;
	private final InterestService interestService;
	private final MajorInterestService majorInterestService;
	private final InterestRelationService interestRelationService;

	private final PointService pointService;
	private final PointWalletService pointWalletService;
	private final StreakService streakService;
	private final AchieverService achieverService;
	private final AssignQuestService assignQuestService;
	private final RepeatQuestService repeatQuestService;

	@Scheduled(cron = "0 0 2 * * *")
	public void memberDataCleanUp() {
		List<Member> deletedMembers = memberService.findMembersForDelete();

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
		assignQuestService.deleteAllByParticipantId(ParticipantId.of(member.uuid()));
		repeatQuestService.deleteAllByParticipantId(ParticipantId.of(member.uuid()));

		// Interest 관련 데이터 삭제
		interestRelationService.deleteAllByRegistrantId(RegistrantId.of(member.uuid()));
		majorInterestService.deleteAllByRegistrantId(RegistrantId.of(member.uuid()));
		interestService.deleteAllByRegistrantId(RegistrantId.of(member.uuid()));

		// Point 관련 데이터 삭제
		pointService.deleteAllByTransactorId(TransactorId.of(member.uuid()));
		pointWalletService.deletePointWalletByTransactorId(TransactorId.of(member.uuid()));

		// Streak 관련 데이터 삭제
		streakService.deleteAllByAchieverId(AchieverId.of(member.uuid()));
		achieverService.deleteAchiever(AchieverId.of(member.uuid()));

		// 이미지 파일 삭제
		imageService.deleteImage(member.getProfileImage().getUrl());
		imageService.deleteImage(member.getProfileBanner().getUrl());

		// member 최종 삭제
		memberService.deleteMemberPermanently(member.getId());
	}
}
