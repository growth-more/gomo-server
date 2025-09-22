package com.gomo.app.interest.application.port;

import java.util.List;
import java.util.UUID;

import com.gomo.app.interest.application.port.dto.InterestDto;

public interface ReadInterestPortIn {

	InterestDto find(UUID interestId);

	List<InterestDto> findAll(UUID registrantId);
}
