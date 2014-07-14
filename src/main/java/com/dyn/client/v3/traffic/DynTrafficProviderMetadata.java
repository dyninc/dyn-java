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
package com.dyn.client.v3.traffic;

import static org.jclouds.Constants.PROPERTY_MAX_REDIRECTS;
import static org.jclouds.Constants.PROPERTY_RETRY_DELAY_START;

import java.net.URI;
import java.util.Properties;

import org.jclouds.providers.ProviderMetadata;
import org.jclouds.providers.internal.BaseProviderMetadata;

/**
 * Implementation of {@link org.jclouds.types.ProviderMetadata} for DynECT Managed DNS.
 * @author Adrian Cole
 */
public class DynTrafficProviderMetadata extends BaseProviderMetadata {

   public static Builder builder() {
      return new Builder();
   }

   @Override
   public Builder toBuilder() {
      return builder().fromProviderMetadata(this);
   }

   public DynTrafficProviderMetadata() {
      super(builder());
   }

   public DynTrafficProviderMetadata(Builder builder) {
      super(builder);
   }

   public static Properties defaultProperties() {
      Properties properties = new Properties();
      // job polling occurs via redirect loop and can take a while
      properties.setProperty(PROPERTY_MAX_REDIRECTS, "100");
      properties.setProperty(PROPERTY_RETRY_DELAY_START, "200");
      return properties;
   }

   public static class Builder extends BaseProviderMetadata.Builder {

      protected Builder() {
         id("dyn-traffic")
         .name("DynECT Managed DNS")
         .apiMetadata(new DynTrafficApiMetadata())
         .homepage(URI.create("http://dyn.com/dns/dynect-managed-dns/"))
         .console(URI.create("https://manage.dynect.net"))
         .iso3166Codes("US-CA", "US-VA")
         .endpoint("https://api2.dynect.net/REST")
         .defaultProperties(DynTrafficProviderMetadata.defaultProperties());
      }

      @Override
      public DynTrafficProviderMetadata build() {
         return new DynTrafficProviderMetadata(this);
      }

      @Override
      public Builder fromProviderMetadata(ProviderMetadata in) {
         super.fromProviderMetadata(in);
         return this;
      }

   }
}
