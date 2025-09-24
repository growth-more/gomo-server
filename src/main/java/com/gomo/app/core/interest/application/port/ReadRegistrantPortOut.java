package com.gomo.app.core.interest.application.port;

import java.util.UUID;

import com.gomo.app.core.interest.application.port.dto.RegistrantDto;

public interface ReadRegistrantPortOut {

	RegistrantDto find(UUID registrantId);
}
