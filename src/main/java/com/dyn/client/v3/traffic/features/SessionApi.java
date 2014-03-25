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
package com.dyn.client.v3.traffic.features;

import javax.inject.Named;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.jclouds.rest.annotations.BinderParam;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.Headers;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.binders.BindToJsonPayload;

import com.dyn.client.v3.traffic.DynTrafficFallbacks.FalseOn400;
import com.dyn.client.v3.traffic.domain.Session;
import com.dyn.client.v3.traffic.domain.SessionCredentials;
import com.dyn.client.v3.traffic.filters.AlwaysAddContentType;
import com.dyn.client.v3.traffic.filters.AlwaysAddUserAgent;

/**
 * @see <a
 *      href="https://manage.dynect.net/help/docs/api2/rest/resources/Session.html"
 *      />
 * @author Adrian Cole
 */
@Headers(keys = "API-Version", values = "{jclouds.api-version}")
@Path("/Session")
@RequestFilters({ AlwaysAddUserAgent.class, AlwaysAddContentType.class })
public interface SessionApi {

   @Named("POST:Session")
   @POST
   @SelectJson("data")
   Session login(@BinderParam(BindToJsonPayload.class) SessionCredentials credentials);

   @Named("GET:Session")
   @GET
   @Fallback(FalseOn400.class)
   boolean isValid(@HeaderParam("Auth-Token") String token);

   @Named("DELETE:Session")
   @DELETE
   void logout(@HeaderParam("Auth-Token") String token);
}
