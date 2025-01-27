package com.gomo.app.common.authentication;

import java.util.UUID;

import com.gomo.app.member.domain.model.RoleType;

public class MemberContext {

	private static final ThreadLocal<SessionMember> context = new ThreadLocal<>();

	public static void addSessionMember(SessionMember sessionMember) {
		context.set(sessionMember);
	}

	public static SessionMember getSessionMember() {
		// TODO <jhl221123>: 테스트를 위해 사용자 픽스처 정보를 반환한다. 인증 기능 추가 후 수정이 필요하다.
		return SessionMember.of(
			UUID.fromString("a10581ce-d721-11ef-a8a5-2508e2a6438b"),
			"gomotest@naver.com",
			"@GOMOTEST",
			"gomotest",
			RoleType.ROLE_MEMBER.name()
		);
		// return context.get();
	}

	public static void clear() {
		context.remove();
	}
}
