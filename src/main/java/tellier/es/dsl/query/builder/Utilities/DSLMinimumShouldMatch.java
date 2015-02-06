/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/
package tellier.es.dsl.query.builder.Utilities;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Minimum should match parameters for match, multi match and common queries
 */
public class DSLMinimumShouldMatch {
    private String lowFreq;
    private String highFreq;

    public final String LOW_FREQ = "low_freq";
    public final String HIGH_FREQ = "high_freq";

    public DSLMinimumShouldMatch(String lowFreq, String highFreq) {
        this.lowFreq = lowFreq;
        this.highFreq = highFreq;
    }

    public JsonObject getParameterAsJson() {
        JsonObject body = new JsonObject();
        body.add(LOW_FREQ, new JsonPrimitive(lowFreq));
        body.add(HIGH_FREQ, new JsonPrimitive(highFreq));
        return body;
    }
}
