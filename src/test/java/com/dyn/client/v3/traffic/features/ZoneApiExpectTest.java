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
import static com.dyn.client.common.DynClientVersion.VERSION;
import static com.google.common.net.HttpHeaders.ACCEPT;
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static com.google.common.net.HttpHeaders.USER_AGENT;
import static javax.ws.rs.HttpMethod.DELETE;
import static javax.ws.rs.HttpMethod.GET;
import static javax.ws.rs.HttpMethod.POST;
import static javax.ws.rs.HttpMethod.PUT;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.OK;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpResponse;
import org.testng.annotations.Test;

import com.dyn.client.v3.traffic.DynTrafficApi;
import com.dyn.client.v3.traffic.domain.CreatePrimaryZone;
import com.dyn.client.v3.traffic.domain.Job;
import com.dyn.client.v3.traffic.internal.BaseDynTrafficApiExpectTest;
import com.dyn.client.v3.traffic.parse.DeleteZoneChangesResponseTest;
import com.dyn.client.v3.traffic.parse.DeleteZoneResponseTest;
import com.dyn.client.v3.traffic.parse.GetZoneResponseTest;
import com.dyn.client.v3.traffic.parse.ListZonesResponseTest;
/**
 * @author Adrian Cole
 */
@Test(groups = "unit", testName = "ZoneApiExpectTest")
public class ZoneApiExpectTest extends BaseDynTrafficApiExpectTest {
   HttpRequest get = HttpRequest.builder().method(GET)
                                .endpoint("https://api2.dynect.net/REST/Zone/jclouds.org")
                                .addHeader("API-Version", "3.3.8")
                                .addHeader(USER_AGENT, VERSION)
                                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                                .addHeader("Auth-Token", authToken).build();

   HttpResponse getResponse = HttpResponse.builder().statusCode(OK.getStatusCode())
         .payload(payloadFromResourceWithContentType("/get_zone.json", APPLICATION_JSON)).build();

   public void testGetWhenResponseIs2xx() {
      DynTrafficApi success = requestsSendResponses(createSession, createSessionResponse, get, getResponse);
      assertEquals(success.getZoneApi().get("jclouds.org").toString(),
                   new GetZoneResponseTest().expected().toString());
   }

   HttpRequest create = HttpRequest.builder().method(POST)
         .endpoint("https://api2.dynect.net/REST/Zone/jclouds.org")
         .addHeader("API-Version", "3.3.8")
         .addHeader(USER_AGENT, VERSION)
         .addHeader(ACCEPT, APPLICATION_JSON)
         .addHeader("Auth-Token", authToken)
         .payload(stringPayload("{\"rname\":\"jimmy@jclouds.org\",\"serial_style\":\"increment\",\"ttl\":3600}"))
         .build();   

   HttpResponse createResponse = HttpResponse.builder().statusCode(OK.getStatusCode())
         .payload(payloadFromResourceWithContentType("/new_zone.json", APPLICATION_JSON)).build();

   public void testCreateWhenResponseIs2xx() {
      DynTrafficApi success = requestsSendResponses(createSession, createSessionResponse, create, createResponse);
      assertEquals(success.getZoneApi().scheduleCreate(CreatePrimaryZone.builder()
                                                                        .fqdn("jclouds.org")
                                                                        .contact("jimmy@jclouds.org")
                                                                        .build()), Job.success(285351593l));
   }

   public void testCreateWithContactWhenResponseIs2xx() {
      DynTrafficApi success = requestsSendResponses(createSession, createSessionResponse, create, createResponse);
      assertEquals(success.getZoneApi().scheduleCreateWithContact("jclouds.org", "jimmy@jclouds.org"), Job.success(285351593l));
   }

   public void testGetWhenResponseIs404() {
      DynTrafficApi fail = requestsSendResponses(createSession, createSessionResponse, get, notFound);
      assertNull(fail.getZoneApi().get("jclouds.org"));
   }

   HttpRequest list = HttpRequest.builder().method(GET)
                                 .endpoint("https://api2.dynect.net/REST/Zone")
                                 .addHeader("API-Version", "3.3.8")
                                 .addHeader(USER_AGENT, VERSION)
                                 .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                                 .addHeader("Auth-Token", authToken).build();  

   HttpResponse listResponse = HttpResponse.builder().statusCode(OK.getStatusCode())
         .payload(payloadFromResourceWithContentType("/list_zones.json", APPLICATION_JSON)).build();

   public void testListWhenResponseIs2xx() {
      DynTrafficApi success = requestsSendResponses(createSession, createSessionResponse, list, listResponse);
      assertEquals(success.getZoneApi().list().toString(),
                   new ListZonesResponseTest().expected().toString());
   }

   HttpRequest deleteChanges = HttpRequest.builder().method(DELETE)
                                          .endpoint("https://api2.dynect.net/REST/ZoneChanges/jclouds.org")
                                          .addHeader("API-Version", "3.3.8")
                                          .addHeader(USER_AGENT, VERSION)
                                          .addHeader(ACCEPT, APPLICATION_JSON)
                                          .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                                          .addHeader("Auth-Token", authToken).build();

   HttpResponse deleteChangesResponse = HttpResponse.builder().statusCode(OK.getStatusCode())
         .payload(payloadFromResourceWithContentType("/delete_zone_changes.json", APPLICATION_JSON)).build();

   public void testDeleteChangesWhenResponseIs2xx() {
      DynTrafficApi success = requestsSendResponses(createSession, createSessionResponse, deleteChanges, deleteChangesResponse);
      assertEquals(success.getZoneApi().deleteChanges("jclouds.org").toString(),
                   new DeleteZoneChangesResponseTest().expected().toString());
   }

   HttpRequest delete = HttpRequest.builder().method(DELETE)
                                   .endpoint("https://api2.dynect.net/REST/Zone/jclouds.org")
                                   .addHeader("API-Version", "3.3.8")
                                   .addHeader(USER_AGENT, VERSION)
                                   .addHeader(ACCEPT, APPLICATION_JSON)
                                   .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                                   .addHeader("Auth-Token", authToken).build();

   HttpResponse deleteResponse = HttpResponse.builder().statusCode(OK.getStatusCode())
         .payload(payloadFromResourceWithContentType("/delete_zone.json", APPLICATION_JSON)).build();

   public void testDeleteWhenResponseIs2xx() {
      DynTrafficApi success = requestsSendResponses(createSession, createSessionResponse, delete, deleteResponse);
      assertEquals(success.getZoneApi().delete("jclouds.org").toString(),
                   new DeleteZoneResponseTest().expected().toString());
   }

   public void testDeleteWhenResponseIs404() {
      DynTrafficApi fail = requestsSendResponses(createSession, createSessionResponse, delete, notFound);
      assertNull(fail.getZoneApi().delete("jclouds.org"));
   }

   HttpRequest publish = HttpRequest.builder().method(PUT)
         .endpoint("https://api2.dynect.net/REST/Zone/jclouds.org")
         .addHeader("API-Version", "3.3.8")
         .addHeader(USER_AGENT, VERSION)
         .addHeader("Auth-Token", authToken)
         .payload(stringPayload("{\"publish\":true}"))
         .build();   

   public void testPublishWhenResponseIs2xx() {
      DynTrafficApi success = requestsSendResponses(createSession, createSessionResponse, publish, getResponse);
      assertEquals(success.getZoneApi().publish("jclouds.org").toString(),
                   new GetZoneResponseTest().expected().toString());
   }

   HttpRequest freeze = HttpRequest.builder().method(PUT)
         .endpoint("https://api2.dynect.net/REST/Zone/jclouds.org")
         .addHeader("API-Version", "3.3.8")
         .addHeader(USER_AGENT, VERSION)
         .addHeader(ACCEPT, APPLICATION_JSON)
         .addHeader("Auth-Token", authToken)
         .payload(stringPayload("{\"freeze\":true}"))
         .build();   

   public void testFreezeWhenResponseIs2xx() {
      DynTrafficApi success = requestsSendResponses(createSession, createSessionResponse, freeze, deleteResponse);
      assertEquals(success.getZoneApi().freeze("jclouds.org").toString(),
                   new DeleteZoneResponseTest().expected().toString());
   }

   HttpRequest thaw = HttpRequest.builder().method(PUT)
         .endpoint("https://api2.dynect.net/REST/Zone/jclouds.org")
         .addHeader("API-Version", "3.3.8")
         .addHeader(USER_AGENT, VERSION)
         .addHeader(ACCEPT, APPLICATION_JSON)
         .addHeader("Auth-Token", authToken)
         .payload(stringPayload("{\"thaw\":true}"))
         .build();   

   public void testThawWhenResponseIs2xx() {
      DynTrafficApi success = requestsSendResponses(createSession, createSessionResponse, thaw, deleteResponse);
      assertEquals(success.getZoneApi().thaw("jclouds.org").toString(),
                   new DeleteZoneResponseTest().expected().toString());
   }
}
