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
package com.dyn.client.v3.messaging;

import java.net.URI;
import java.util.Properties;

import org.jclouds.apis.ApiMetadata;
import org.jclouds.rest.internal.BaseHttpApiMetadata;

import com.dyn.client.v3.messaging.config.DynMessagingHttpApiModule;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;

/**
 * Implementation of {@link ApiMetadata} for Dyn Messaging API
 */
public class DynMessagingApiMetadata extends BaseHttpApiMetadata<DynMessagingApi> {
   
   @Override
   public Builder toBuilder() {
      return new Builder().fromApiMetadata(this);
   }

   public DynMessagingApiMetadata() {
      this(new Builder());
   }

   protected DynMessagingApiMetadata(Builder builder) {
      super(builder);
   }

   public static Properties defaultProperties() {
      Properties properties = BaseHttpApiMetadata.defaultProperties();
      return properties;
   }

   public static class Builder extends BaseHttpApiMetadata.Builder<DynMessagingApi, Builder> {

      protected Builder() {
          id("dyn-messaging")
         .name("Dyn Messaging API")
         .identityName("${customer}:${userName}")
         .credentialName("${password}")
         .documentation(URI.create("https://help.dynect.net/api/"))
         .version("1.0")
         .defaultEndpoint("https://emailapi.dynect.net/rest/json")
         .defaultProperties(DynMessagingApiMetadata.defaultProperties())
         .defaultModules(ImmutableSet.<Class<? extends Module>>builder()
                                     .add(DynMessagingHttpApiModule.class).build());
      }
      
      @Override
      public DynMessagingApiMetadata build() {
         return new DynMessagingApiMetadata(this);
      }

      @Override
      protected Builder self() {
         return this;
      }
   }
}
