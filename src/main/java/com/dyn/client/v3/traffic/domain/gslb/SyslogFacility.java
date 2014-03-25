package com.dyn.client.v3.traffic.domain.gslb;

public enum SyslogFacility {
	KERN("kern"), USER("user"), MAIL("mail"), DAEMON("daemon"), AUTH("auth"), SYSLOG(
			"syslog"), LPR("lpr"), NEWS("news"), UUCP("uucp"), CRON("cron"), AUTHPRIV(
			"authpriv"), FTP("ftp"), NTP("ntp"), SECURITY("security"), CONSOLE(
			"console"), LOCAL0("local0"), LOCAL1("local1"), LOCAL2("local2"), LOCAL3(
			"local3"), LOCAL4("local4"), LOCAL5("local5"), LOCAL6("local6"), LOCAL7(
			"local7");

	private final String facilityCode;

	private SyslogFacility(String facilityCode) {
		this.facilityCode = facilityCode;
	}

	public String getFacilityCode() {
		return facilityCode;
	}
}
