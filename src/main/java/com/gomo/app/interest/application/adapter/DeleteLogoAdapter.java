package com.gomo.app.interest.application.adapter;

import com.gomo.app.common.Adapter;
import com.gomo.app.image.port.DeleteImagePortIn;
import com.gomo.app.interest.application.port.DeleteLogoPortOut;

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
