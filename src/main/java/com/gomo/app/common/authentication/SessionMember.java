package com.gomo.app.common.authentication;

import java.util.UUID;

import lombok.Getter;

@Getter
public class SessionMember {

	private UUID memberId;
	private String email;
	private String handle;
	private String name;
	private String role;

	private SessionMember(
		UUID memberId,
		String email,
		String handle,
		String name,
		String role
	) {
		this.memberId = memberId;
		this.email = email;
		this.handle = handle;
		this.name = name;
		this.role = role;
	}

	public static SessionMember of(
		UUID memberId,
		String email,
		String handle,
		String name,
		String role
	) {
		return new SessionMember(memberId, email, handle, name, role);
	}
}
