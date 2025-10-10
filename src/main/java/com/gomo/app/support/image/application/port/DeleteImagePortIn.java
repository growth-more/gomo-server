package com.gomo.app.support.image.application.port;

public interface DeleteImagePortIn {

	/**
	 * Deletes an image specified by its URL.
	 *
	 * @param imageUrl The public URL of the image to be deleted.
	 */
	void delete(String imageUrl);
}
