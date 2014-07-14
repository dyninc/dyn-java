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

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;

import com.dyn.client.common.AlwaysAddApiKey;
import com.dyn.client.common.AlwaysAddUserAgent;
import com.google.inject.name.Named;

/**
 * Dyn Messaging Senders API
 */
@RequestFilters({ AlwaysAddUserAgent.class, AlwaysAddApiKey.class })
public interface SendersApi {
	@Named("GetSendersList")
	@GET
	@Path("/senders")
	@SelectJson("senders")
	@Consumes(APPLICATION_JSON)
	List<Map<String, Object>> list(@QueryParam("startindex") int startIndex);

	@Named("GetSenderDetails")
	@GET
	@Path("/senders/details")
	@SelectJson("data")
	@Consumes(APPLICATION_JSON)
	Map<String, Object> getDetails(
			@FormParam("emailaddress") String emailAddress);

	@Named("GetSenderStatus")
	@GET
	@Path("/senders/status")
	@SelectJson("data")
	@Consumes(APPLICATION_JSON)
	Map<String, Object> getStatus(@FormParam("emailaddress") String emailAddress);

	@Named("CreateOrUpdateSender")
	@POST
	@Path("/senders")
	@SelectJson("data")
	@Produces(APPLICATION_FORM_URLENCODED)
	@Consumes(APPLICATION_JSON)
	List<Map<String, Object>> createOrUpdate(
			@FormParam("emailaddress") String emailAddress,
			@FormParam("seeding") String seeding);

	@Named("DeleteSender")
	@POST
	@Path("/senders/delete")
	@SelectJson("data")
	@Produces(APPLICATION_FORM_URLENCODED)
	@Consumes(APPLICATION_JSON)
	List<Map<String, Object>> delete(
			@FormParam("emailaddress") String emailAddress);

	@Named("SetSenderDKIM")
	@POST
	@Path("/senders/dkim")
	@SelectJson("data")
	@Produces(APPLICATION_FORM_URLENCODED)
	@Consumes(APPLICATION_JSON)
	Map<String, Object> setDkim(@FormParam("emailaddress") String emailAddress,
			@FormParam("dkim") String dkim);
}
