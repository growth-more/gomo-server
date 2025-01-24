package com.gomo.app.point.domain.service;

import java.util.List;

import com.gomo.app.common.domain.DomainService;
import com.gomo.app.common.dto.PageRequest;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.point.domain.model.Point;
import com.gomo.app.point.domain.repository.PointRepository;
import com.gomo.app.point.presentation.response.AvailableReadPointResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class PointService {

	private final PointRepository pointRepository;

	public AvailableReadPointResponse findAvailablePoints(MemberId memberId) {
		return null;
	}

	public List<Point> findAll(MemberId memberId, PageRequest request) {
		return null;
	}
}
