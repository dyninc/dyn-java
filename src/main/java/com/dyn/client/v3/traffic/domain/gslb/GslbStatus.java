package com.dyn.client.v3.traffic.domain.gslb;

public enum GslbStatus {
	UNK("unk"), OK("ok"), TROUBLE("trouble"), FAILOVER("failover");

	private final String statusCode;

	private GslbStatus(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusCode() {
		return statusCode;
	}
}
