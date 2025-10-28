package com.gomo.app.core.interest.adapter.out.client;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.interest.application.port.out.DeleteLogoPort;
import com.gomo.app.support.image.application.port.DeleteImagePortIn;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class DeleteImageClient implements DeleteLogoPort {

	private final DeleteImagePortIn deleteImagePortIn;

	@Override
	public void delete(String logoUrl) {
		deleteImagePortIn.delete(logoUrl);
	}
}
