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
package com.dyn.client.v3.messaging.handlers;

import static org.jclouds.http.HttpUtils.closeClientButKeepContentStream;
import static org.jclouds.http.HttpUtils.releasePayload;

import org.jclouds.http.HttpCommand;
import org.jclouds.http.HttpErrorHandler;
import org.jclouds.http.HttpResponse;
import org.jclouds.http.HttpResponseException;

public class DynMessagingErrorHandler implements HttpErrorHandler {
	public void handleError(HttpCommand command, HttpResponse response) {
		Exception exception = new HttpResponseException(command, response);
		try {
			byte[] data = closeClientButKeepContentStream(response);
			String message = data != null ? new String(data) : null;

			if (message != null) {
				exception = new HttpResponseException(command, response,
						message);
			}
		} finally {
			releasePayload(response);
			command.setException(exception);
		}
	}
}
