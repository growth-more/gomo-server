package com.gomo.app.point.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gomo.app.common.dto.PageRequest;
import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.point.application.HistoryReadPointUseCase;
import com.gomo.app.point.presentation.response.HistoryListPointResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/points")
@Presentation
public class PointApi {

	private final HistoryReadPointUseCase historyReadPointUseCase;

	@GetMapping("/histories")
	public ResponseEntity<HistoryListPointResponse> findHistories(@RequestParam PageRequest pageRequest) {
		return null;
	}
}
