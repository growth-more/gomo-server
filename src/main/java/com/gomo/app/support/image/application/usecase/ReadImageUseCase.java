package com.gomo.app.support.image.application.usecase;

import java.util.Set;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.support.image.application.port.ManageImagePortOut;
import com.gomo.app.support.image.application.port.ReadImagePortIn;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadImageUseCase implements ReadImagePortIn {

	private final ManageImagePortOut manageImagePortOut;

	@Override
	public Set<String> readAllImages() {
		return manageImagePortOut.findAllImageUrls();
	}
}
