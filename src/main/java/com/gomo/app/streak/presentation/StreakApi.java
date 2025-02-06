package com.gomo.app.streak.presentation;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gomo.app.common.authentication.MemberContext;
import com.gomo.app.common.authentication.SessionMember;
import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.streak.application.ReadStreakUseCase;
import com.gomo.app.streak.domain.model.AchieverId;
import com.gomo.app.streak.presentation.response.ListStreakResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/streaks")
@Presentation
public class StreakApi {

	private final ReadStreakUseCase readStreakUseCase;

	@GetMapping
	public ResponseEntity<ListStreakResponse> findAllByStreakType(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
		SessionMember sessionMember = MemberContext.getSessionMember();
		ListStreakResponse response = readStreakUseCase.findAll(AchieverId.of(sessionMember.getId()), startDate, endDate);
		return ResponseEntity.ok(response);
	}
}
