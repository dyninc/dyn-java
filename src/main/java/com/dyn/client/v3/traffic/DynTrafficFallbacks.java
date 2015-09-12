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

import org.jclouds.Fallback;

import static com.google.common.base.Predicates.equalTo;
import static com.google.common.base.Throwables.propagate;
import static org.jclouds.http.HttpUtils.returnValueOnCodeOrNull;

/**
 * 
 * @author Adrian Cole
 */
public final class DynTrafficFallbacks {
   private DynTrafficFallbacks() {
   }

   public static class FalseOn400 implements Fallback<Boolean> {
      @Override
      public Boolean createOrPropagate(Throwable t) throws Exception {
         if (returnValueOnCodeOrNull(t, false, equalTo(400)) != null)
            return false;
         throw propagate(t);
      }
   }
}
