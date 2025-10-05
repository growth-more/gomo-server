package com.gomo.app.support.event.application.port;

public interface CreateEventEntryPortIn {

	Long create(String eventName, String payload, long timestamp);
}
