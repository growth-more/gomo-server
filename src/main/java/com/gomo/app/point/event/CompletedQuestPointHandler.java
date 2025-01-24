package com.gomo.app.point.event;

import org.springframework.context.event.EventListener;

import com.gomo.app.common.event.EventHandler;
import com.gomo.app.point.domain.repository.PointRepository;
import com.gomo.app.quest.event.PointQuestCompletedEvent;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EventHandler
public class CompletedQuestPointHandler {

	private final PointRepository pointRepository;

	@EventListener(PointQuestCompletedEvent.class)
	public void handle(PointQuestCompletedEvent event) {

	}
}
