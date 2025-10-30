package com.gomo.app.support.image.application.usecase;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.support.image.application.port.ImageDeleter;
import com.gomo.app.support.image.application.port.ManageImagePortOut;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class DeleteImageUseCase implements ImageDeleter {

	private final ManageImagePortOut manageImagePortOut;

	@Override
	public void delete(String imageUrl) {
		manageImagePortOut.delete(imageUrl);
	}
}
