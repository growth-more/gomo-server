package com.gomo.app.event;

public abstract class Event {

	private long timestamp;

	protected Event() {}

	protected Event(long timestamp) {
		this.timestamp = timestamp;
	}
}
