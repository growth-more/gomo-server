package com.gomo.app.core.member.application.port;

public interface EncodePasswordPortOut {

	/**
	 * Encodes a given plain-text password.
	 *
	 * @param rawPassword The plain-text password to be encoded.
	 * @return The resulting encoded (hashed and salted) password as a string.
	 */
	String encode(String rawPassword);
}
