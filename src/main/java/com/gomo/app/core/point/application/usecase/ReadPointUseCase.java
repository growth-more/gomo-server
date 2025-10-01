package com.gomo.app.core.point.application.usecase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.web.PageRequest;
import com.gomo.app.core.point.application.port.dto.ListPointDto;
import com.gomo.app.core.point.application.port.dto.PointDto;
import com.gomo.app.core.point.domain.repository.PointRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadPointUseCase {

	private final PointRepository pointRepository;

	public ListPointDto findAll(UUID transactorId, PageRequest pageRequest) {
		List<PointDto> points = pointRepository.findAllByTransactorId(
				transactorId.toString(),
				Optional.ofNullable(pageRequest.getLastElementId()).map(UUID::toString).orElse(null),
				pageRequest.getSize())
			.stream()
			.map(PointDto::from)
			.toList();
		return ListPointDto.of(points, getLastElementId(points));
	}

	@Nullable
	private UUID getLastElementId(List<PointDto> list) {
		if (list.isEmpty()) {
			return null;
		}
		return list.getLast().id();
	}
}
