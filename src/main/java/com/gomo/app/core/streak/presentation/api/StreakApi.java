package com.gomo.app.core.streak.presentation.api;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.streak.application.port.dto.ListStreakDto;
import com.gomo.app.core.streak.application.usecase.ReadStreakUseCase;
import com.gomo.app.core.streak.presentation.api.response.ListStreakResponse;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/streaks")
@CoreApi
public class StreakApi {

	private final ReadStreakUseCase readStreakUseCase;

	@GetMapping
	public ResponseEntity<ListStreakResponse> findAllByStreakType(@Auth AuthInfo authInfo, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
		ListStreakDto dto = readStreakUseCase.findAll(authInfo.getPrincipalId(), startDate, endDate);
		return ResponseEntity.ok(ListStreakResponse.from(dto));
	}
}
