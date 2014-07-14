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
package com.dyn.client.v3.traffic.internal;
import static com.dyn.client.common.DynClientVersion.VERSION;
import static com.google.common.net.HttpHeaders.USER_AGENT;
import static javax.ws.rs.HttpMethod.POST;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;

import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpResponse;
import org.jclouds.http.config.SSLModule;
import org.jclouds.io.Payload;
import org.jclouds.io.Payloads;
import org.jclouds.rest.ConfiguresHttpApi;
import org.jclouds.rest.internal.BaseRestApiExpectTest;

import com.dyn.client.v3.traffic.config.DynTrafficHttpApiModule;
import com.google.inject.Module;

/**
 * Base class for writing DynECT Expect tests
 * 
 * @author Adrian Cole
 */
public class BaseDynTrafficExpectTest<T> extends BaseRestApiExpectTest<T> {
   public BaseDynTrafficExpectTest() {
      provider = "dyn-traffic";
      identity = "jclouds:joe";
      credential = "letmein";
   }

   @Override
   protected Module createModule() {
      return new TestDynECTHttpApiModule();
   }

   @ConfiguresHttpApi
   private static final class TestDynECTHttpApiModule extends DynTrafficHttpApiModule {
      @Override
      protected void configure() {
         install(new SSLModule());
         super.configure();
      }
   }

   public static Payload stringPayload(String json) {
      Payload p = Payloads.newPayload(json);
      p.getContentMetadata().setContentType(APPLICATION_JSON);
      return p;
   }

   @Override
   protected HttpRequestComparisonType compareHttpRequestAsType(HttpRequest input) {
      return HttpRequestComparisonType.JSON;
   }

   protected String authToken = "FFFFFFFFFF";

   protected HttpRequest createSession = HttpRequest.builder()
         .method(POST)
         .endpoint("https://api2.dynect.net/REST/Session")
         .addHeader("API-Version", "3.3.8")
         .addHeader(USER_AGENT, VERSION)
         .payload(
               payloadFromStringWithContentType(
                     "{\"customer_name\":\"jclouds\",\"user_name\":\"joe\",\"password\":\"letmein\"}", APPLICATION_JSON))
         .build();

   protected HttpResponse createSessionResponse = HttpResponse.builder().statusCode(OK.getStatusCode())
         .payload(payloadFromResourceWithContentType("/create_session.json", APPLICATION_JSON)).build();

   protected HttpResponse notFound = HttpResponse.builder().statusCode(NOT_FOUND.getStatusCode()).build();
}
