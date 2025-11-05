package com.gomo.app.batch;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberCleanupScheduler {

	// private final ImageDeleter imageDeleter;
	// private final PointRepository pointRepository;
	// private final PointWalletRepository pointWalletRepository;
	// private final AssignQuestRepository assignQuestRepository;
	// private final RepeatQuestRepository repeatQuestRepository;
	// private final InterestRelationRepository interestRelationRepository;
	// private final MajorInterestRepository majorInterestRepository;
	// private final InterestRepository interestRepository;
	// private final StreakRepository streakRepository;
	// private final AchieverRepository achieverRepository;
	// private final MemberRepository memberRepository;
	//
	// @Value("${app.members.retentionDays}")
	// private long retentionDays;
	//
	// @Scheduled(cron = "0 0 2 * * *")
	// public void memberDataCleanUp() {
	// 	List<Member> deletedMembers = memberRepository.findByDeletedAtBefore(LocalDateTime.now().minusDays(retentionDays));
	//
	// 	for (Member member : deletedMembers) {
	// 		cleanupMemberDataAsync(member);
	// 	}
	// }
	//
	// @Async("memberCleanupExecutor")
	// public CompletableFuture<Void> cleanupMemberDataAsync(Member member) {
	// 	try {
	// 		deleteMemberAndRelatedData(member);
	// 		return CompletableFuture.completedFuture(null);
	// 	} catch (Exception e) {
	// 		return CompletableFuture.failedFuture(e);
	// 	}
	// }
	//
	// private void deleteMemberAndRelatedData(Member member) {
	// 	// Quest 관련 데이터 삭제
	// 	assignQuestRepository.deleteAllByParticipantId(member.getId());
	// 	repeatQuestRepository.deleteAllByParticipantId(member.getId());
	//
	// 	// Interest 관련 데이터 삭제
	// 	interestRelationRepository.deleteAllByRegistrantId(member.getId());
	// 	majorInterestRepository.deleteAllByRegistrantId(member.getId());
	// 	interestRepository.deleteAllByRegistrantId(member.getId());
	//
	// 	// Point 관련 데이터 삭제
	// 	pointRepository.deleteAllByTransactorId(member.getId());
	// 	pointWalletRepository.deletePointWalletByTransactorId(member.getId());
	//
	// 	// Streak 관련 데이터 삭제
	// 	streakRepository.deleteAllByAchieverId(member.getId());
	// 	achieverRepository.deleteByAchieverId(member.getId());
	//
	// 	// 이미지 파일 삭제
	// 	imageDeleter.delete(member.profileImageUrl());
	// 	imageDeleter.delete(member.profileBannerUrl());
	//
	// 	// member 최종 삭제
	// 	memberRepository.deleteById(member.getId());
	// }
}
