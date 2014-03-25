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
package com.dyn.client.v3.traffic.domain.redirect;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.beans.ConstructorProperties;
import java.util.Map;

import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ImmutableMap;

/**
 * Encapsulates data related to Dyn HTTP redirect service.
 */
public class HttpRedirect extends ForwardingMap<String, Object> {
   private final String url;
   private final int code;
   private final String keepUri;
   private final String fqdn;
   private final String zone;

   @ConstructorProperties({ "url", "code", "keep_uri", "fqdn", "zone" })
   private HttpRedirect(String url, int code, String keepUri, String fqdn, String zone) {
      this.url = checkNotNull(url, "url");
      checkArgument(code == 301 || code == 302, "code of %s must be 301 or 302", code);
      checkArgument("Y".equals(keepUri) || "N".equals(keepUri), "code of %s must be 'Y' or 'N'", keepUri);
      this.code = code;
      this.keepUri = keepUri;
      this.fqdn = checkNotNull(fqdn, "fqdn");
      this.zone = checkNotNull(zone, "zone");
      
      this.delegate = ImmutableMap.<String, Object> builder()
            .put("url", url)
            .put("code", code)
            .put("keep_uri", keepUri)
            .put("fqdn", fqdn)
            .put("zone", zone).build();
   }

   /**
    * The URL to redirect to
    * 
    * @return a valid base URL
    */
   public String getUrl() {
      return url;
   }

   /**
    * The HTTP redirect code to use
    * 
    * @return 301 (permanent) or 302 (temporary)
    */
   public int getCode() {
	  return code;
   }

   /**
    * Whether to append the URI path from the original request ('Y' or 'N')
    */
   public String getKeepUri() {
	  return keepUri;
   }

   /**
    * FQDN string that the redirect corresponds to
    */
   public String getFqdn() {
	  return fqdn;
   }

   /**
    * DNS zone that this redirect applies to
    */
   public String getZone() {
	  return zone;
   }

   public ImmutableMap<String, Object> getDelegate() {
	  return delegate;
   }

   public static final class Builder {
      private String url;
      private int code;
      private boolean keepUri;
      private String fqdn;
      private String zone;

      /**
       * @see HttpRedirect#getUrl()
       */
      public HttpRedirect.Builder url(String url) {
         this.url = url;
         return this;
      }

      /**
       * @see HttpRedirect#getCode()
       */
      public HttpRedirect.Builder code(int code) {
         this.code = code;
         return this;
      }

      /**
       * @see HttpRedirect#getKeepUri()
       */
      public HttpRedirect.Builder keepUri(boolean keepUri) {
         this.keepUri = keepUri;
         return this;
      }

      /**
       * @see HttpRedirect#getFqdn()
       */
      public HttpRedirect.Builder fqdn(String fqdn) {
         this.fqdn = fqdn;
         return this;
      }

      /**
       * @see HttpRedirect#getZone()
       */
      public HttpRedirect.Builder zone(String zone) {
         this.zone = zone;
         return this;
      }

      public HttpRedirect build() {
         return new HttpRedirect(url, code, keepUri ? "Y" : "N", fqdn, zone);
      }
   }

   public static HttpRedirect.Builder builder() {
      return new Builder();
   }

   // transient to avoid serializing by default, for example in json
   private final transient ImmutableMap<String, Object> delegate;

   @Override
   protected Map<String, Object> delegate() {
      return delegate;
   }
}
