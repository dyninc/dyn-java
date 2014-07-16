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
package com.dyn.client.v3.messaging;

import java.util.Map;

import org.jclouds.ContextBuilder;
import org.jclouds.providers.ProviderMetadata;
import org.jclouds.providers.Providers;

import com.dyn.client.v3.messaging.features.AccountsApi;
import com.dyn.client.v3.messaging.features.RecipientsApi;
import com.dyn.client.v3.messaging.features.ReportApi;
import com.dyn.client.v3.messaging.features.SendMailApi;
import com.dyn.client.v3.messaging.features.UniqueReportApi;
import com.google.common.collect.ImmutableMap;

public class MessagingApiSmokeTest {
	public static void main(String[] args) {
		// Configure/Authenticate the Dyn Java Messaging client instance
		ProviderMetadata meta = Providers.withId("dyn-messaging");
		ContextBuilder ctx = ContextBuilder.newBuilder(meta);

		ctx.credentials(/* intentionally blank -> */"", "yourapikey");
		DynMessagingApi dyn = ctx.buildApi(DynMessagingApi.class);

		SendMailApi send = dyn.getSendMailApi();
		System.out.println("send : "
				+ send.sendMessage("fromperson@example.org",
						"recipient@example.org", "the subject", "hi text",
						"<p>hi html</p>", "ccuser@example.org",
						"reply-to@example.org", null));

		AccountsApi accounts = dyn.getAccountsApi();

		System.out.println("accounts list : " + accounts.list(0));
		System.out.println("accounts xheaders : " + accounts.getXHeaders());

		RecipientsApi recipients = dyn.getRecipientsApi();

		System.out.println("recipient status : "
				+ recipients.status("recip@example.com"));
		System.out.println("recipient activate : "
				+ recipients.activate("recip@example.com"));

		Map<String, ReportApi> apis = ImmutableMap
				.<String, ReportApi> builder()
				.put("bounces", dyn.getBounceReportApi())
				.put("clicks", dyn.getClicksReportApi())
				.put("complaints", dyn.getComplaintsReportApi())
				.put("delivered", dyn.getDeliveredReportApi())
				.put("issues", dyn.getIssuesReportApi())
				.put("opens", dyn.getOpensReportApi())
				.put("sent", dyn.getSentReportApi()).build();

		// test the regular report functionality
		for (Map.Entry<String, ReportApi> entry : apis.entrySet()) {
			String name = entry.getKey();
			ReportApi api = entry.getValue();

			System.out.println(name + " list : "
					+ api.list("2014-01-01", "2014-11-01", 0));
			System.out.println(name + " count : "
					+ api.count("2014-01-01", "2014-11-01"));
		}

		Map<String, UniqueReportApi> unique = ImmutableMap
				.<String, UniqueReportApi> builder()
				.put("clicks", dyn.getClicksReportApi())
				.put("opens", dyn.getOpensReportApi()).build();

		// unique report functionality
		for (Map.Entry<String, UniqueReportApi> entry : unique.entrySet()) {
			String name = entry.getKey();
			UniqueReportApi api = entry.getValue();

			System.out.println(name + " list unique : "
					+ api.listUnique("2014-01-01", "2014-11-01", 0));
			System.out.println(name + " count unique : "
					+ api.countUnique("2014-01-01", "2014-11-01"));
		}
	}
}
