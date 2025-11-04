package com.gomo.app.core.streak.adapter.in.api;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.common.session.Session;
import com.gomo.app.common.session.SessionInfo;
import com.gomo.app.core.streak.adapter.in.api.response.ListStreakResponse;
import com.gomo.app.core.streak.application.port.dto.ListStreakDto;
import com.gomo.app.core.streak.application.port.in.StreakReader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/streaks")
@CoreApi
public class StreakApi {

	private final StreakReader streakReader;

	@GetMapping
	public ResponseEntity<ListStreakResponse> findAllByStreakType(@Session SessionInfo sessionInfo, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
		ListStreakDto dto = streakReader.findAll(sessionInfo.getPrincipalId(), startDate, endDate);
		return ResponseEntity.ok(ListStreakResponse.from(dto));
	}
}
