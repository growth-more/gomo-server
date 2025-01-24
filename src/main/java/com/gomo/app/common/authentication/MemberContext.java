package com.gomo.app.common.authentication;

public class MemberContext {

	private static final ThreadLocal<SessionMember> context = new ThreadLocal<>();

	public static void addSessionMember(SessionMember sessionMember) {
		context.set(sessionMember);
	}

	public static SessionMember getSessionMember() {
		return context.get();
	}

	public static void clear() {
		context.remove();
	}
}
