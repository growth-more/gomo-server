package com.gomo.app.point.application;

import org.springframework.data.domain.PageRequest;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.point.domain.service.PointService;
import com.gomo.app.point.presentation.response.HistoryListPointResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class HistoryReadPointUseCase {

	private final PointService pointService;

	public HistoryListPointResponse findAll(MemberId memberId, PageRequest pageRequest) {
		return null;
	}
}
