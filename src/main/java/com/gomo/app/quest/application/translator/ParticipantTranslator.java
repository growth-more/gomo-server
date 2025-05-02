package com.gomo.app.quest.application.translator;

import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.QuestProperty;
import com.gomo.app.quest.domain.model.Participant;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.QuestQuota;

public class ParticipantTranslator {

	public static Participant from(Member member) {
		return Participant.of(
			ParticipantId.of(member.uuid()),
			transferFrom(member.getQuestProperty())
		);
	}

	// TODO <jhl221123>: 여기는 수동 생성 즉, 시스템적 제한을 사용하도록 수정이 필요합니다.
	private static QuestQuota transferFrom(QuestProperty questProperty) {
		return QuestQuota.of(
			questProperty.getDailyThreshold().getThreshold(),
			questProperty.getWeeklyThreshold().getThreshold(),
			questProperty.getMonthlyThreshold().getThreshold()
		);
	}
}
