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
package com.dyn.client.v3.messaging.config;

import static org.jclouds.http.HttpUtils.closeClientButKeepContentStream;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URI;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.jclouds.Constants;
import org.jclouds.http.HttpErrorHandler;
import org.jclouds.http.HttpResponse;
import org.jclouds.http.HttpUtils;
import org.jclouds.http.IOExceptionRetryHandler;
import org.jclouds.http.annotation.ClientError;
import org.jclouds.http.annotation.Redirection;
import org.jclouds.http.annotation.ServerError;
import org.jclouds.http.handlers.DelegatingErrorHandler;
import org.jclouds.http.handlers.DelegatingRetryHandler;
import org.jclouds.http.internal.HttpWire;
import org.jclouds.http.internal.JavaUrlHttpCommandExecutorService;
import org.jclouds.io.ContentMetadataCodec;
import org.jclouds.rest.ConfiguresHttpApi;
import org.jclouds.rest.config.HttpApiModule;

import com.dyn.client.v3.messaging.DynMessagingApi;
import com.dyn.client.v3.messaging.handlers.DynMessagingErrorHandler;
import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.ListeningExecutorService;

/**
 * Configures the Dyn connection.
 */
@ConfiguresHttpApi
public class DynMessagingHttpApiModule extends HttpApiModule<DynMessagingApi> {
   public DynMessagingHttpApiModule() {
   }

   @Override
   protected void bindErrorHandlers() {
      bind(HttpErrorHandler.class).annotatedWith(Redirection.class).to(DynMessagingErrorHandler.class);
      bind(HttpErrorHandler.class).annotatedWith(ClientError.class).to(DynMessagingErrorHandler.class);
      bind(HttpErrorHandler.class).annotatedWith(ServerError.class).to(DynMessagingErrorHandler.class);
   }

   @Override
   protected void configure() {
      super.configure();
      bind(JavaUrlHttpCommandExecutorService.class).to(SillyRabbit200sAreForSuccess.class);
   }

   // dynect returns the following as a 200.
   // {"status": "failure", "data": {}, "job_id": 274509427, "msgs":
   // [{"INFO": "token: This session already has a job running", "SOURCE":
   // "API-B", "ERR_CD": "OPERATION_FAILED", "LVL": "ERROR"}]}
   @Singleton
   private static class SillyRabbit200sAreForSuccess extends JavaUrlHttpCommandExecutorService {
      @Inject
      private SillyRabbit200sAreForSuccess(HttpUtils utils, ContentMetadataCodec contentMetadataCodec,
            @Named(Constants.PROPERTY_IO_WORKER_THREADS) ListeningExecutorService ioExecutor,
            DelegatingRetryHandler retryHandler, IOExceptionRetryHandler ioRetryHandler,
            DelegatingErrorHandler errorHandler, HttpWire wire, @Named("untrusted") HostnameVerifier verifier,
            @Named("untrusted") Supplier<SSLContext> untrustedSSLContextProvider, Function<URI, Proxy> proxyForURI)
            throws SecurityException, NoSuchFieldException {
         super(utils, contentMetadataCodec, ioExecutor, retryHandler, ioRetryHandler, errorHandler, wire, verifier,
               untrustedSSLContextProvider, proxyForURI);
      }

      /**
       * synchronized to prevent multiple callers from overlapping requests on the same session
       */
      @Override
      protected synchronized HttpResponse invoke(HttpURLConnection connection) throws IOException, InterruptedException {
         HttpResponse response = super.invoke(connection);
         if (response.getStatusCode() == 200) {
            byte[] data = closeClientButKeepContentStream(response);
            String message = data != null ? new String(data, "UTF-8") : null;
            
            System.out.println("message: " + message);
            
            if (message != null && !message.startsWith("{\"response\":{\"status\":200,\"message\":\"OK\",")) {
               response = response.toBuilder().statusCode(400).build();
            }
         }
         return response;
      }
   }
}
