package com.gomo.app.streak.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.streak.domain.model.StreakType;
import com.gomo.app.streak.domain.service.StreakService;
import com.gomo.app.streak.presentation.response.ListStreakResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadStreakUseCase {

	private final StreakService streakService;

	public ListStreakResponse findAll(MemberId memberId, StreakType streakType) {
		return null;
	}
}
