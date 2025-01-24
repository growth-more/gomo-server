package com.gomo.app.streak.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.streak.application.ReadStreakUseCase;
import com.gomo.app.streak.domain.model.StreakType;
import com.gomo.app.streak.presentation.response.ListStreakResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/streaks")
@Presentation
public class StreakApi {

	private final ReadStreakUseCase readStreakUseCase;

	@GetMapping
	public ResponseEntity<ListStreakResponse> findAllByStreakType(@RequestParam StreakType streakType) {
		return null;
	}
}
