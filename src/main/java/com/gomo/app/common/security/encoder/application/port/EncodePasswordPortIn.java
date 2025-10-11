package com.gomo.app.common.security.encoder.application.port;

public interface EncodePasswordPortIn {

	/**
	 * Encodes a given plain-text password.
	 *
	 * @param rawPassword The plain-text password to be encoded.
	 * @return The resulting encoded (hashed and salted) password as a string.
	 */
	String encode(String rawPassword);
}
