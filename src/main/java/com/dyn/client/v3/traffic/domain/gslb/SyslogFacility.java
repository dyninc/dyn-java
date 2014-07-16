/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
