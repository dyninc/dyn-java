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
package com.dyn.client.v3.traffic.predicates;

import static com.dyn.client.v3.traffic.domain.RecordId.recordIdBuilder;
import static com.dyn.client.v3.traffic.predicates.RecordPredicates.typeEquals;

import org.testng.annotations.Test;

import com.dyn.client.v3.traffic.domain.RecordId;

/**
 * 
 * @author Adrian Cole
 */
@Test(groups = "unit")
public class RecordPredicatesTest {
   RecordId recordId = recordIdBuilder()
                               .zone("adrianc.zone.dynecttest.jclouds.org")
                               .fqdn("adrianc.zone.dynecttest.jclouds.org")
                               .type("SOA")
                               .id(50976579l).build();

   @Test
   public void testTypeEqualsWhenEqual() {
      assert typeEquals("SOA").apply(recordId);
   }

   @Test
   public void testTypeEqualsWhenNotEqual() {
      assert !typeEquals("NS").apply(recordId);
   }
}
