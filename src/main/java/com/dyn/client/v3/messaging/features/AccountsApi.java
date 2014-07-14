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
package com.dyn.client.v3.messaging.features;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.jclouds.javax.annotation.Nullable;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;

import com.dyn.client.common.AlwaysAddApiKey;
import com.dyn.client.common.AlwaysAddUserAgent;

/**
 * Dyn Messaging Accounts API
 */
@RequestFilters({ AlwaysAddUserAgent.class, AlwaysAddApiKey.class })
public interface AccountsApi {
	@Named("GetAccountsList")
	@GET
	@Path("/accounts")
	@SelectJson("accounts")
	@Consumes(APPLICATION_JSON)
	List<Map<String, Object>> list(@QueryParam("startindex") int startIndex);

	@Named("CreateOrUpdateAccount")
	@POST
	@Path("/accounts")
	@SelectJson("data")
	@Produces(APPLICATION_FORM_URLENCODED)
	@Consumes(APPLICATION_JSON)
	Map<String, Object> createOrUpdate(@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("companyname") String companyName,
			@FormParam("phone") String phone,
			@FormParam("address") @Nullable String address,
			@FormParam("city") @Nullable String city,
			@FormParam("state") @Nullable String state,
			@FormParam("zipcode") @Nullable String zipcode,
			@FormParam("country") @Nullable String country,
			@FormParam("timezone") @Nullable String timeZone,
			@FormParam("bounceurl") @Nullable String bounceUrl,
			@FormParam("spamurl") @Nullable String spamUrl,
			@FormParam("unsubscribeurl") @Nullable String unsubscribeUrl,
			@FormParam("trackopens") @Nullable String trackOpens,
			@FormParam("tracklinks") @Nullable String trackLinks,
			@FormParam("trackunsubscribes") @Nullable String trackUnsubscribes,
			@FormParam("generatenewapikey") @Nullable String generateNewApiKey);

	@Named("DeleteAccount")
	@POST
	@Path("/accounts/delete")
	@SelectJson("data")
	@Produces(APPLICATION_FORM_URLENCODED)
	@Consumes(APPLICATION_JSON)
	List<Map<String, Object>> delete(@FormParam("username") String username);

	@Named("GetXHeaders")
	@GET
	@Path("/accounts/xheaders")
	@SelectJson("data")
	@Consumes(APPLICATION_JSON)
	Map<String, Object> getXHeaders();

	@Named("SetXHeaders")
	@POST
	@Path("/accounts/xheaders")
	@SelectJson("data")
	@Produces(APPLICATION_FORM_URLENCODED)
	@Consumes(APPLICATION_JSON)
	List<Map<String, Object>> setXHeaders(
			@FormParam("xheader1") String xheader1,
			@FormParam("xheader2") String xheader2,
			@FormParam("xheader3") String xheader3,
			@FormParam("xheader4") String xheader4);
}
