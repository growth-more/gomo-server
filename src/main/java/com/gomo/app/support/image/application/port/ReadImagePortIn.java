package com.gomo.app.support.image.application.port;

import java.util.Set;

public interface ReadImagePortIn {

	/**
	 * Retrieves all available image URLs.
	 *
	 * @return A set of all image URLs. Returns an empty set if no images exist.
	 */
	Set<String> readAllImages();
}
