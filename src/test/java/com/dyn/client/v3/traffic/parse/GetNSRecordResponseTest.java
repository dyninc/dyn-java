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
package com.dyn.client.v3.traffic.parse;
import static com.dyn.client.v3.traffic.domain.rdata.NSData.ns;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.jclouds.rest.annotations.SelectJson;
import org.testng.annotations.Test;

import com.dyn.client.v3.traffic.domain.Record;
import com.dyn.client.v3.traffic.domain.rdata.NSData;
import com.dyn.client.v3.traffic.internal.BaseDynTrafficParseTest;

/**
 * @author Adrian Cole
 */
@Test(groups = "unit")
public class GetNSRecordResponseTest extends BaseDynTrafficParseTest<Record<NSData>> {

   @Override
   public String resource() {
      return "/get_record_ns.json";
   }

   @Override
   @SelectJson("data")
   @Consumes(MediaType.APPLICATION_JSON)
   public Record<NSData> expected() {
      return Record.<NSData> builder()
                   .zone("egg.org")
                   .fqdn("egg.org")
                   .type("NS")
                   .id(50959331)
                   .ttl(86400)
                   .rdata(ns("ns4.p28.dynect.net."))
                   .build();
   }
}
