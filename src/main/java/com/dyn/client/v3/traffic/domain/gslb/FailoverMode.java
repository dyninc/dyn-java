package com.dyn.client.v3.traffic.domain.gslb;

public enum FailoverMode {
	IP("ip"), CNAME("cname"), REGION("region"), GLOBAL("global");

	private final String failoverCode;

	private FailoverMode(String failoverCode) {
		this.failoverCode = failoverCode;
	}

	public String getFailoverCode() {
		return failoverCode;
	}
}
