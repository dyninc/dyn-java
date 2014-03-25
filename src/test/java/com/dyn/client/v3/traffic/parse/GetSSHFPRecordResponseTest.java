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

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.jclouds.rest.annotations.SelectJson;
import org.testng.annotations.Test;

import com.dyn.client.v3.traffic.domain.Record;
import com.dyn.client.v3.traffic.domain.rdata.SSHFPData;
import com.dyn.client.v3.traffic.internal.BaseDynTrafficParseTest;

/**
 * @author Adrian Cole
 */
@Test(groups = "unit")
public class GetSSHFPRecordResponseTest extends BaseDynTrafficParseTest<Record<SSHFPData>> {

    @Override
    public String resource() {
        return "/get_record_sshfp.json";
    }

    @Override
    @SelectJson("data")
    @Consumes(MediaType.APPLICATION_JSON)
    public Record<SSHFPData> expected() {
        return Record.<SSHFPData> builder().zone("adrianc.zone.dynecttest.jclouds.org")
                .fqdn("_http._tcp.www.jclouds.org.").type("SSHFP").id(50976579l).ttl(3600)
                .rdata(SSHFPData.builder().algorithm(2).fptype(1).fingerprint("190E37C5B5DB9A1C455E648A41AF3CC83F99F102").build()).build();
    }
}
