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
package com.dyn.client.v3.traffic.binders;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.jclouds.http.Uris.uriBuilder;

import java.net.URI;

import javax.inject.Inject;

import com.dyn.client.v3.traffic.domain.redirect.HttpRedirect;
import org.jclouds.http.HttpRequest;
import org.jclouds.json.Json;
import org.jclouds.rest.Binder;

import com.google.common.collect.ImmutableMap;

public class CreateHttpRedirectBinder implements Binder {
   private final Json json;

   @Inject
   CreateHttpRedirectBinder(Json json){
      this.json = checkNotNull(json, "json");
   }

   @SuppressWarnings("unchecked")
   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Object arg) {
      HttpRedirect in = HttpRedirect.class.cast(checkNotNull(arg, "redirect to create"));
      URI path = uriBuilder(request.getEndpoint()).build(in.getDelegate());
      return (R) request.toBuilder()
                        .endpoint(path)
                        .payload(json.toJson(ImmutableMap.of(
                        		"code", in.getCode(), "keep_uri", in.getKeepUri(), "url", in.getUrl()))).build();
   }
}
