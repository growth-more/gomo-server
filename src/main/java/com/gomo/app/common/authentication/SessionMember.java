package com.gomo.app.common.authentication;

import java.util.UUID;

import lombok.Getter;

@Getter
public class SessionMember {

	private UUID id;
	private String email;
	private String handle;
	private String name;
	private String role;

	private SessionMember(
		UUID id,
		String email,
		String handle,
		String name,
		String role
	) {
		this.id = id;
		this.email = email;
		this.handle = handle;
		this.name = name;
		this.role = role;
	}

	public static SessionMember of(
		UUID id,
		String email,
		String handle,
		String name,
		String role
	) {
		return new SessionMember(id, email, handle, name, role);
	}
}
