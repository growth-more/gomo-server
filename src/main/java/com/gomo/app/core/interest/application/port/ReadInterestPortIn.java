package com.gomo.app.core.interest.application.port;

import java.util.List;
import java.util.UUID;

import com.gomo.app.core.interest.application.port.dto.InterestDto;
import com.gomo.app.core.interest.exception.InterestNotFoundException;

public interface ReadInterestPortIn {

	/**
	 * @return interest info
	 * @exception InterestNotFoundException if not found
	 */
	InterestDto find(UUID interestId);

	List<InterestDto> findAll(UUID registrantId);
}
