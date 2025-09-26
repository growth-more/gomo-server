package com.gomo.app.core.point.application.port;

import java.util.UUID;

public interface CreatePointWalletPortIn {

	UUID create(UUID transactorId);
}
