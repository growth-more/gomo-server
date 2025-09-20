package com.gomo.app.quest.application.adapter;

import java.util.List;

import com.gomo.app.common.Adapter;
import com.gomo.app.quest.application.port.ReadActiveParticipantPort;
import com.gomo.app.quest.application.port.dto.ActiveParticipantDto;

@Adapter
class ReadActiveParticipantAdapter implements ReadActiveParticipantPort {

	@Override
	public List<ActiveParticipantDto> findAll() {
		// todo jhl221123: 회원 모듈과 연동
		return null;
	}
}
