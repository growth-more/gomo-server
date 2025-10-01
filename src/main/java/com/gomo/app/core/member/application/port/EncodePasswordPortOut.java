package com.gomo.app.core.member.application.port;

public interface EncodePasswordPortOut {

	/**
	 * @return encoded password
	 */
	String encode(String rawPassword);
}
