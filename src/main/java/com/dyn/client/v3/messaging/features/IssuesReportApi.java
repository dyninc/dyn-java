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

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;

import com.dyn.client.common.AlwaysAddApiKey;
import com.dyn.client.common.AlwaysAddUserAgent;

/**
 * Dyn Messaging Issues Report API
 */
@RequestFilters({ AlwaysAddUserAgent.class, AlwaysAddApiKey.class })
public interface IssuesReportApi extends ReportApi {
	@Named("GetIssuesCount")
	@GET
	@Path("/reports/issues/count")
	@SelectJson("count")
	@Consumes(APPLICATION_JSON)
	@Override
	Long count(@QueryParam("starttime") String startTime,
			@QueryParam("endtime") String endTime);

	@Named("GetIssuesList")
	@GET
	@Path("/reports/issues")
	@SelectJson("issue")
	@Consumes(APPLICATION_JSON)
	@Override
	List<Map<String, Object>> list(@QueryParam("starttime") String startTime,
			@QueryParam("endtime") String endTime,
			@QueryParam("startindex") int startIndex);
}
