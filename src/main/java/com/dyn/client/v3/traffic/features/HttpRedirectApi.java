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

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.jclouds.Fallbacks.NullOnNotFoundOr404;

import com.dyn.client.common.AlwaysAddContentType;
import com.dyn.client.common.AlwaysAddUserAgent;
import com.dyn.client.v3.traffic.DynTrafficExceptions.JobStillRunningException;
import com.dyn.client.v3.traffic.binders.CreateHttpRedirectBinder;
import com.dyn.client.v3.traffic.domain.Job;
import com.dyn.client.v3.traffic.domain.redirect.HttpRedirect;
import com.dyn.client.v3.traffic.filters.SessionManager;
import com.dyn.client.v3.traffic.functions.ExtractLastPathComponent;

import org.jclouds.javax.annotation.Nullable;
import org.jclouds.rest.annotations.BinderParam;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.Headers;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.annotations.Transform;

import com.google.common.collect.FluentIterable;

/**
 * @author Sunny Gleason
 * @author Adrian Cole
 */
@Headers(keys = "API-Version", values = "{jclouds.api-version}")
@RequestFilters({ AlwaysAddUserAgent.class, AlwaysAddContentType.class, SessionManager.class })
public interface HttpRedirectApi {
   /**
    * Retrieves a list of HTTP Redirects in the given zone.
    * 
    * @throws JobStillRunningException
    *            if a different job in the session is still running
    */
   @Named("GetAllHttpRedirects")
   @GET
   @Path("/HTTPRedirect/{zone}/")
   @SelectJson("data")
   @Transform(ExtractLastPathComponent.class)
   FluentIterable<String> list() throws JobStillRunningException;

   /**
    * Schedules addition of a new HTTP redirect into the current session. Calling
    * {@link ZoneApi#publish(String)} will publish the zone, creating the
    * redirect.
    * 
    * @param newRedirect
    *           record to create
    * @return job relating to the scheduled creation.
    * @throws JobStillRunningException
    *            if a different job in the session is still running
    */
   @Named("CreateHttpRedirect")
   @POST
   @Path("/HTTPRedirect/{zone}/{fqdn}/")
   @Consumes(APPLICATION_JSON)
   @Produces(APPLICATION_JSON)
   Job scheduleCreate(@BinderParam(CreateHttpRedirectBinder.class) HttpRedirect newRedirect) throws JobStillRunningException;

   /**
    * Schedules update of an existing HTTP redirect into the current session. Calling
    * {@link ZoneApi#publish(String)} will publish the zone, creating the
    * redirect.
    * 
    * @param newRedirect
    *           record to create
    * @return job relating to the scheduled creation.
    * @throws JobStillRunningException
    *            if a different job in the session is still running
    */
   @Named("UpdateHttpRedirect")
   @PUT
   @Path("/HTTPRedirect/{zone}/{fqdn}/")
   @Fallback(NullOnNotFoundOr404.class)
   @Consumes(APPLICATION_JSON)
   @Produces(APPLICATION_JSON)
   Job scheduleUpdate(@BinderParam(CreateHttpRedirectBinder.class) HttpRedirect newRedirect) throws JobStillRunningException;

   /**
    * Schedules deletion of a redirect into the current session. Calling
    * {@link ZoneApi#publish(String)} will publish the changes, deleting the
    * record.
    * 
    * @param fqdn
    *           fqdn redirect to delete
    * @return job relating to the scheduled deletion or null, if the record
    *         never existed.
    * @throws JobStillRunningException
    *            if a different job in the session is still running
    */
   @Nullable
   @Named("DeleteHttpRedirect")
   @DELETE
   @Path("/HTTPRedirect/{zone}/{fqdn}/")
   @Fallback(NullOnNotFoundOr404.class)
   @Consumes(APPLICATION_JSON)
   @Produces(APPLICATION_JSON)
   Job scheduleDelete(@PathParam("fqdn") String fqdn) throws JobStillRunningException;

   /**
    * Gets the HttpRedirect or null if not present.
    * 
    * @param fqdn
    *           {@link RecordId#getFQDN()}
    * @return null if not found
    * @throws JobStillRunningException
    *            if a different job in the session is still running
    */
   @Named("GetHttpRedirect")
   @GET
   @Path("/HTTPRedirect/{zone}/{fqdn}/")
   @SelectJson("data")
   @Fallback(NullOnNotFoundOr404.class)
   @Nullable
   HttpRedirect getHttpRedirect(@PathParam("fqdn") String fqdn) throws JobStillRunningException;
}
