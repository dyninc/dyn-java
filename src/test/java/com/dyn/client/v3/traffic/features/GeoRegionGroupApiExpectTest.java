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
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static com.google.common.net.HttpHeaders.USER_AGENT;
import static javax.ws.rs.HttpMethod.GET;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.OK;
import static org.testng.Assert.assertEquals;

import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpResponse;
import org.testng.annotations.Test;

import com.dyn.client.v3.traffic.DynTrafficApi;
import com.dyn.client.v3.traffic.internal.BaseDynTrafficApiExpectTest;
import com.dyn.client.v3.traffic.parse.GetGeoRegionGroupResponseTest;
import com.dyn.client.v3.traffic.parse.ListGeoRegionGroupsResponseTest;
import com.google.common.net.HttpHeaders;

/**
 * @author Adrian Cole
 */
@Test(groups = "unit", testName = "GeoRegionGroupApiExpectTest")
public class GeoRegionGroupApiExpectTest extends BaseDynTrafficApiExpectTest {

   HttpRequest list = HttpRequest.builder().method(GET).endpoint("https://api2.dynect.net/REST/GeoRegionGroup/srv")
         .addHeader("API-Version", "3.3.8").addHeader(USER_AGENT, DYN_TRAFFIC_USER_AGENT).addHeader(CONTENT_TYPE, APPLICATION_JSON)
         .addHeader("Auth-Token", authToken).build();

   HttpResponse listResponse = HttpResponse.builder().statusCode(OK.getStatusCode())
         .payload(payloadFromResourceWithContentType("/list_geo_regiongroups.json", APPLICATION_JSON)).build();

   public void testListWhenResponseIs2xx() {
      DynTrafficApi success = requestsSendResponses(createSession, createSessionResponse, list, listResponse);
      assertEquals(success.getGeoRegionGroupApiForService("srv").list().toString(),
            new ListGeoRegionGroupsResponseTest().expected().toString());
   }

   HttpRequest get = HttpRequest.builder().method(GET)
         .endpoint("https://api2.dynect.net/REST/GeoRegionGroup/srv/Everywhere%20Else")
         .addHeader("API-Version", "3.3.8").addHeader(USER_AGENT, DYN_TRAFFIC_USER_AGENT).addHeader(CONTENT_TYPE, APPLICATION_JSON)
         .addHeader("Auth-Token", authToken).build();

   HttpResponse getResponse = HttpResponse.builder().statusCode(OK.getStatusCode())
         .payload(payloadFromResourceWithContentType("/get_geo_regiongroup.json", APPLICATION_JSON)).build();

   public void testGetWhenResponseIs2xx() {
      DynTrafficApi success = requestsSendResponses(createSession, createSessionResponse, get, getResponse);
      assertEquals(success.getGeoRegionGroupApiForService("srv").get("Everywhere Else").toString(),
            new GetGeoRegionGroupResponseTest().expected().toString());
   }
}
