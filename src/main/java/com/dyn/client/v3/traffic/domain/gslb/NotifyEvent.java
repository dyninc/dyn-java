package com.dyn.client.v3.traffic.domain.gslb;

public enum NotifyEvent {
	IP("ip"), SVC("svc"), NOSRV("nosrv");

	private final String eventCode;

	private NotifyEvent(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getEventCode() {
		return eventCode;
	}
}
