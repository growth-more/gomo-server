package com.gomo.app.streak.presentation;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gomo.app.common.Presentation;
import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
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
	public ResponseEntity<ListStreakResponse> findAllByStreakType(@Auth AuthInfo authInfo, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
		ListStreakResponse response = readStreakUseCase.findAll(AchieverId.of(authInfo.getMemberId()), startDate, endDate);
		return ResponseEntity.ok(response);
	}
}
