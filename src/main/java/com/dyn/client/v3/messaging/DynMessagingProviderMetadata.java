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

import org.jclouds.providers.ProviderMetadata;
import org.jclouds.providers.internal.BaseProviderMetadata;

/**
 * Implementation of {@link org.jclouds.types.ProviderMetadata} for Dyn Messaging API
 */
public class DynMessagingProviderMetadata extends BaseProviderMetadata {

	public static Builder builder() {
		return new Builder();
	}

	@Override
	public Builder toBuilder() {
		return builder().fromProviderMetadata(this);
	}

	public DynMessagingProviderMetadata() {
		super(builder());
	}

	public DynMessagingProviderMetadata(Builder builder) {
		super(builder);
	}

	public static Properties defaultProperties() {
		Properties properties = new Properties();
		return properties;
	}

	public static class Builder extends BaseProviderMetadata.Builder {

		protected Builder() {
			id("dyn-messaging")
				.name("Dyn Messaging API")
				.apiMetadata(new DynMessagingApiMetadata())
				.homepage(URI.create("http://dyn.com/message-management/"))
				.console(URI.create("https://email.dynect.net"))
				.iso3166Codes("US-CA", "US-VA")
				.endpoint("https://emailapi.dynect.net/rest/json")
				.defaultProperties(
						DynMessagingProviderMetadata.defaultProperties());
		}

		@Override
		public DynMessagingProviderMetadata build() {
			return new DynMessagingProviderMetadata(this);
		}

		@Override
		public Builder fromProviderMetadata(ProviderMetadata in) {
			super.fromProviderMetadata(in);
			return this;
		}

	}
}
