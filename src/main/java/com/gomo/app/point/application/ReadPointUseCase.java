package com.gomo.app.point.application;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.dto.PageRequest;
import com.gomo.app.point.domain.model.TransactorId;
import com.gomo.app.point.domain.repository.PointRepository;
import com.gomo.app.point.presentation.response.ListPointResponse;
import com.gomo.app.point.presentation.response.ReadPointResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadPointUseCase {

	private final PointRepository pointRepository;

	public ListPointResponse findAll(TransactorId transactorId, PageRequest pageRequest) {
		List<ReadPointResponse> responses = pointRepository.findAllByTransactorId(
				transactorId.toString(),
				Optional.ofNullable(pageRequest.getLastElementId()).map(UUID::toString).orElse(null),
				pageRequest.getSize())
			.stream()
			.map(ReadPointResponse::of)
			.toList();
		return ListPointResponse.of(responses);
	}
}
