package com.gomo.app.support.image.application.port.in;

import java.util.Set;

public interface ImageReader {

	/**
	 * Retrieves all available image URLs.
	 *
	 * @return A set of all image URLs. Returns an empty set if no images exist.
	 */
	Set<String> readAllImages();
}
