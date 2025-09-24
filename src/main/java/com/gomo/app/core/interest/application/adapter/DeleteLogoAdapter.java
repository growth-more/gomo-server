package com.gomo.app.core.interest.application.adapter;

import com.gomo.app.common.Adapter;
import com.gomo.app.core.interest.application.port.DeleteLogoPortOut;
import com.gomo.app.support.image.port.DeleteImagePortIn;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class DeleteLogoAdapter implements DeleteLogoPortOut {

	private final DeleteImagePortIn deleteImagePortIn;

	@Override
	public void delete(String logoUrl) {
		deleteImagePortIn.delete(logoUrl);
	}
}
