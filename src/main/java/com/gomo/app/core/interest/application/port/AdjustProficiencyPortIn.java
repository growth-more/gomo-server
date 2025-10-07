package com.gomo.app.core.interest.application.port;

import java.util.UUID;

public interface AdjustProficiencyPortIn {

	void adjust(UUID interestId, int deltaScore);
}
