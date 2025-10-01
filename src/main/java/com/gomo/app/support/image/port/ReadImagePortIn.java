package com.gomo.app.support.image.port;

import java.util.Set;

public interface ReadImagePortIn {

	/**
	 * @return all image URLs
	 */
	Set<String> readAllImages();
}
