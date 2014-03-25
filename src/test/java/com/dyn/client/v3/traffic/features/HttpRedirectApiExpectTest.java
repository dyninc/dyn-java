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

import static com.dyn.client.v3.traffic.DynTrafficApi.DYN_TRAFFIC_USER_AGENT;
import static com.google.common.net.HttpHeaders.ACCEPT;
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static com.google.common.net.HttpHeaders.USER_AGENT;
import static javax.ws.rs.HttpMethod.DELETE;
import static javax.ws.rs.HttpMethod.GET;
import static javax.ws.rs.HttpMethod.POST;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.OK;
import static org.testng.Assert.assertEquals;

import com.dyn.client.v3.traffic.DynTrafficApi;
import com.dyn.client.v3.traffic.domain.Job;
import com.dyn.client.v3.traffic.domain.redirect.HttpRedirect;
import com.dyn.client.v3.traffic.internal.BaseDynTrafficApiExpectTest;

import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpResponse;
import org.testng.annotations.Test;

/**
 * @author Adrian Cole
 * @author Sunny Gleason
 */
@Test(groups = "unit", testName = "HttpRedirectApiExpectTest")
public class HttpRedirectApiExpectTest extends BaseDynTrafficApiExpectTest {
   HttpRequest get = HttpRequest.builder().method(GET)
                                .endpoint("https://api2.dynect.net/REST/HTTPRedirect/redirect.adriancole.zone.dynecttest.jclouds.org/redirect.adriancole.zone.dynecttest.jclouds.org/")
                                .addHeader("API-Version", "3.3.8")
                                .addHeader(USER_AGENT, DYN_TRAFFIC_USER_AGENT)
                                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                                .addHeader("Auth-Token", authToken).build();

   HttpResponse getResponse = HttpResponse.builder().statusCode(OK.getStatusCode())
         .payload(payloadFromResourceWithContentType("/get_redirect.json", APPLICATION_JSON)).build();

   public void testGetWhenResponseIs2xx() {
      DynTrafficApi success = requestsSendResponses(createSession, createSessionResponse, get, getResponse);
      assertEquals(success.getHttpRedirectApiForZone("redirect.adriancole.zone.dynecttest.jclouds.org").getHttpRedirect("redirect.adriancole.zone.dynecttest.jclouds.org").toString(),
                   HttpRedirect.builder().url("http://foo.com/").code(301).keepUri(true).fqdn("redirect.adriancole.zone.dynecttest.jclouds.org").zone("redirect.adriancole.zone.dynecttest.jclouds.org").build().toString());
   }

   HttpRequest create = HttpRequest.builder().method(POST)
         .endpoint("https://api2.dynect.net/REST/HTTPRedirect/redirect.adriancole.zone.dynecttest.jclouds.org/redirect.adriancole.zone.dynecttest.jclouds.org/")
         .addHeader("API-Version", "3.3.8")
         .addHeader(USER_AGENT, DYN_TRAFFIC_USER_AGENT)
         .addHeader(ACCEPT, APPLICATION_JSON)
         .addHeader("Auth-Token", authToken)
         .payload(stringPayload("{\"code\":301,\"keep_uri\":\"Y\",\"url\":\"http://foo.com/\"}"))
         .build();

   HttpResponse createResponse = HttpResponse.builder().statusCode(OK.getStatusCode())
         .payload(payloadFromResourceWithContentType("/new_redirect.json", APPLICATION_JSON)).build();

   public void testCreateWhenResponseIs2xx() {
	  DynTrafficApi success = requestsSendResponses(createSession, createSessionResponse, create, createResponse);
      assertEquals(success.getHttpRedirectApiForZone("redirect.adriancole.zone.dynecttest.jclouds.org").scheduleCreate(
    		  HttpRedirect.builder().url("http://foo.com/").code(301).keepUri(true).fqdn("redirect.adriancole.zone.dynecttest.jclouds.org")
    		  .zone("redirect.adriancole.zone.dynecttest.jclouds.org").build()), Job.success(387928810l));
   }

   HttpRequest list = HttpRequest.builder().method(GET)
                                 .endpoint("https://api2.dynect.net/REST/HTTPRedirect/redirect.adriancole.zone.dynecttest.jclouds.org/")
                                 .addHeader("API-Version", "3.3.8")
                                 .addHeader(USER_AGENT, DYN_TRAFFIC_USER_AGENT)
                                 .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                                 .addHeader("Auth-Token", authToken).build();  

   HttpResponse listResponse = HttpResponse.builder().statusCode(OK.getStatusCode())
         .payload(payloadFromResourceWithContentType("/list_redirects.json", APPLICATION_JSON)).build();

   public void testListWhenResponseIs2xx() {
	  DynTrafficApi success = requestsSendResponses(createSession, createSessionResponse, list, listResponse);
      assertEquals(success.getHttpRedirectApiForZone("redirect.adriancole.zone.dynecttest.jclouds.org").list().toString(),
                   "[redirect.adriancole.zone.dynecttest.jclouds.org]");
   }

   HttpRequest delete = HttpRequest.builder().method(DELETE)
                                   .endpoint("https://api2.dynect.net/REST/HTTPRedirect/redirect.adriancole.zone.dynecttest.jclouds.org/redirect.adriancole.zone.dynecttest.jclouds.org/")
                                   .addHeader("API-Version", "3.3.8")
                                   .addHeader(USER_AGENT, DYN_TRAFFIC_USER_AGENT)
                                   .addHeader(ACCEPT, APPLICATION_JSON)
                                   .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                                   .addHeader("Auth-Token", authToken).build();

   HttpResponse deleteResponse = HttpResponse.builder().statusCode(OK.getStatusCode())
         .payload(payloadFromResourceWithContentType("/delete_redirect.json", APPLICATION_JSON)).build();

   public void testDeleteWhenResponseIs2xx() {
	  DynTrafficApi success = requestsSendResponses(createSession, createSessionResponse, delete, deleteResponse);
      assertEquals(success.getHttpRedirectApiForZone("redirect.adriancole.zone.dynecttest.jclouds.org").scheduleDelete("redirect.adriancole.zone.dynecttest.jclouds.org").toString(),
                   Job.success(387955406l).toString());
   }
}
