package com.gomo.app.core.streak.application.port;

import java.util.UUID;

public interface CreateAchieverPortIn {

	UUID create(UUID achieverId);
}
