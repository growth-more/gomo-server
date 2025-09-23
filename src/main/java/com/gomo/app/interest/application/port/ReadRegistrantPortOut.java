package com.gomo.app.interest.application.port;

import java.util.UUID;

import com.gomo.app.interest.application.port.dto.RegistrantDto;

public interface ReadRegistrantPortOut {

	RegistrantDto find(UUID registrantId);
}
