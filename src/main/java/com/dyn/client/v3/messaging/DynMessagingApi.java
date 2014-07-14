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

import java.io.Closeable;

import org.jclouds.rest.annotations.Delegate;

import com.dyn.client.v3.messaging.features.AccountsApi;
import com.dyn.client.v3.messaging.features.BouncesReportApi;
import com.dyn.client.v3.messaging.features.ClicksReportApi;
import com.dyn.client.v3.messaging.features.ComplaintsReportApi;
import com.dyn.client.v3.messaging.features.DeliveredReportApi;
import com.dyn.client.v3.messaging.features.IssuesReportApi;
import com.dyn.client.v3.messaging.features.OpensReportApi;
import com.dyn.client.v3.messaging.features.RecipientsApi;
import com.dyn.client.v3.messaging.features.SendMailApi;
import com.dyn.client.v3.messaging.features.SendersApi;
import com.dyn.client.v3.messaging.features.SentReportApi;
import com.dyn.client.v3.messaging.features.SuppressionsApi;

/**
 * Provides access to Dyn Messaging APIs
 */
public interface DynMessagingApi extends Closeable {
	/**
	 * Provides access to Send Mail API
	 */
	@Delegate
	SendMailApi getSendMailApi();

	/**
	 * Provides access to Accounts API
	 */
	@Delegate
	AccountsApi getAccountsApi();

	/**
	 * Provides access to Recipients API
	 */
	@Delegate
	RecipientsApi getRecipientsApi();

	/**
	 * Provides access to Approved Senders API
	 */
	@Delegate
	SendersApi getSendersApi();

	/**
	 * Provides access to Suppressions API
	 */
	@Delegate
	SuppressionsApi getSuppressionsApi();

	/**
	 * Provides access to Delivery 'Sent' Report.
	 */
	@Delegate
	SentReportApi getSentReportApi();

	/**
	 * Provides access to Delivery 'Delivered' Report.
	 */
	@Delegate
	DeliveredReportApi getDeliveredReportApi();

	/**
	 * Provides access to Problems 'Bounce' Report.
	 */
	@Delegate
	BouncesReportApi getBounceReportApi();

	/**
	 * Provides access to Problems 'Complaints' Report.
	 */
	@Delegate
	ComplaintsReportApi getComplaintsReportApi();

	/**
	 * Provides access to Problems 'Issues' Report.
	 */
	@Delegate
	IssuesReportApi getIssuesReportApi();

	/**
	 * Provides access to Engagement 'Opens' Report.
	 */
	@Delegate
	OpensReportApi getOpensReportApi();

	/**
	 * Provides access to Engagement 'Opens' Report.
	 */
	@Delegate
	ClicksReportApi getClicksReportApi();
}
