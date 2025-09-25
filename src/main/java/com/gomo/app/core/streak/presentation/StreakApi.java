package com.gomo.app.core.streak.presentation;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gomo.app.common.arch.Presentation;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;
import com.gomo.app.core.streak.application.ReadStreakUseCase;
import com.gomo.app.core.streak.presentation.response.ListStreakResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/streaks")
@Presentation
public class StreakApi {

	private final ReadStreakUseCase readStreakUseCase;

	@GetMapping
	public ResponseEntity<ListStreakResponse> findAllByStreakType(@Auth AuthInfo authInfo, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
		ListStreakResponse response = readStreakUseCase.findAll(authInfo.getMemberId(), startDate, endDate);
		return ResponseEntity.ok(response);
	}
}
