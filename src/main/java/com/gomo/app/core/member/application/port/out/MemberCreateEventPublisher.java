package com.gomo.app.core.member.application.port.out;

import java.util.UUID;

public interface MemberCreateEventPublisher {

	// TODO [2025-10-29] jhl221123 : spring modulith 도입해 이벤트 기반 통신으로 전환한 후 제거해야합니다.
	void publish(UUID memberId);
}
