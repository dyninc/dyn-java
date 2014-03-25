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
package com.dyn.client.v3.traffic.features;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static java.util.logging.Logger.getAnonymousLogger;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import org.jclouds.JcloudsVersion;
import com.dyn.client.v3.traffic.domain.Job;
import com.dyn.client.v3.traffic.domain.Job.Status;
import com.dyn.client.v3.traffic.domain.Zone;
import com.dyn.client.v3.traffic.domain.redirect.HttpRedirect;
import com.dyn.client.v3.traffic.internal.BaseDynTrafficApiLiveTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;

/**
 * @author Adrian Cole
 * @author Sunny Gleason
 */
@Test(groups = "live", singleThreaded = true, testName = "HttpRedirectApiLiveTest")
public class HttpRedirectApiLiveTest extends BaseDynTrafficApiLiveTest {
   private void checkZone(Zone zone) {
      checkNotNull(zone.getFQDN(), "FQDN cannot be null for a Zone: %s", zone);
      checkNotNull(zone.getSerial(), "Serial cannot be null for a Zone: %s", zone);
   }

   private void getAndVerifyRedirect(String redirectName, int code, String keepUri, String url) {
      HttpRedirect redirect = api.getHttpRedirectApiForZone(fqdn).getHttpRedirect(redirectName);
      checkNotNull(redirect, "redirect was null for Zone: %s", fqdn);
      assertEquals(redirect.getFqdn(), redirectName);
      assertEquals(redirect.getCode(), code);
      assertEquals(redirect.getKeepUri(), keepUri);
      assertEquals(redirect.getUrl(), url);
   }

   @Test
   protected void testEmptyListAndGetHttpRedirects() {
      ImmutableList<String> redirects = api.getHttpRedirectApiForZone(fqdn).list().toList();
      assertEquals(redirects.size(), 0);
   }

   @Test(dependsOnMethods = "testCreatePublishZone")
   protected void testSingularListAndGetHttpRedirects() {
      ImmutableList<String> redirects = api.getHttpRedirectApiForZone(fqdn).list().toList();
      assertEquals(redirects.size(), 1);
      assertEquals(redirects.get(0), redirectName);
   }

   @Test
   public void testGetHttpRedirectWhenNotFound() {
      assertNull(api.getHttpRedirectApiForZone(fqdn).getHttpRedirect(unknownRedirectName));
   }

   @Test
   public void testUpdateHttpRedirectWhenNotFound() {
      assertNull(api.getHttpRedirectApiForZone(fqdn).scheduleUpdate(redirect));
   }

   @Test
   public void testDeleteHttpRedirectWhenNotFound() {
      assertNull(api.getHttpRedirectApiForZone(fqdn).scheduleDelete(unknownRedirectName));
   }

   String fqdn = System.getProperty("user.name").replace('.', '-') + ".zone.dynecttest.jclouds.org";
   String contact = JcloudsVersion.get() + ".jclouds.org";
   String redirectName = "redirect." + fqdn;
   HttpRedirect redirect = HttpRedirect.builder().zone(fqdn).fqdn(redirectName).code(302).keepUri(true).url("http://foo.com/").build();
   String unknownRedirectName = "nodirect." + fqdn;

   @Test
   private void testCreatePublishZone() throws Exception {
      Zone zone = api.getZoneApi().get(fqdn);
      if (zone == null) {
         Job job = api.getZoneApi().scheduleCreateWithContact(fqdn, contact);
         checkNotNull(job, "unable to create zone %s", fqdn);
         getAnonymousLogger().info("created zone: " + job);
         assertEquals(job.getStatus(), Status.SUCCESS);
         assertEquals(api.getJob(job.getId()), job);
         zone = api.getZoneApi().publish(fqdn);
         checkNotNull(zone, "unable to publish zone %s", fqdn);
         getAnonymousLogger().info("published zone: " + zone);
      }
      checkZone(zone);
   }

   @Test(dependsOnMethods = "testCreatePublishZone")
   public void testCreateAndPublishHttpRedirect() throws Exception {
      Thread.sleep(3000); // FIXME - figure out delays
      Job job = api().scheduleCreate(redirect);
      assertEquals(job.getStatus(), Status.SUCCESS);
      assertEquals(api.getJob(job.getId()), job);
      Zone zone = api.getZoneApi().publish(fqdn);
      checkZone(zone);
      getAndVerifyRedirect(redirectName, 302, "Y", "http://foo.com/");
   }

   @Test(dependsOnMethods = "testCreateAndPublishHttpRedirect")
   public void testUpdateAndPublishHttpRedirect() throws Exception {
      Thread.sleep(3000); // FIXME - figure out delays

      try { // FIXME - use dependencies properly
         api().scheduleCreate(redirect);
         api.getZoneApi().publish(fqdn);
      } catch (Exception ignored) {}

      Thread.sleep(3000); // FIXME - figure out delays

      Job job = api().scheduleUpdate(HttpRedirect.builder().zone(fqdn).fqdn(redirectName).code(301).keepUri(false).url("http://bar.com/").build());
      assertEquals(job.getStatus(), Status.SUCCESS);
      assertEquals(api.getJob(job.getId()), job);
      Zone zone = api.getZoneApi().publish(fqdn);
      checkZone(zone);
      getAndVerifyRedirect(redirectName, 301, "N", "http://bar.com/");
   }

   @Test(dependsOnMethods = "testCreateAndPublishHttpRedirect")
   public void testDeleteHttpRedirect() throws Exception {
      Thread.sleep(3000); // FIXME - figure out delays
      getAndVerifyRedirect(redirectName, 302, "Y", "http://foo.com/");
      Job job = api().scheduleDelete(redirectName);
      assertEquals(job.getStatus(), Status.SUCCESS);
      assertEquals(api.getJob(job.getId()), job);
      HttpRedirect redirect = api.getHttpRedirectApiForZone(fqdn).getHttpRedirect(redirectName);
      checkState(redirect == null, "redirect was now null for Zone: %s", fqdn);
   }

   protected HttpRedirectApi api() {
      return api.getHttpRedirectApiForZone(fqdn);
   }

   @Override
   @AfterClass(groups = "live", alwaysRun = true)
   protected void tearDown() {
	  try {
         api.getZoneApi().deleteChanges(fqdn);
         api.getZoneApi().delete(fqdn);
         api.getZoneApi().publish(fqdn);
	  } catch (Exception ignored) {}
	  
      super.tearDown();
   }
}
