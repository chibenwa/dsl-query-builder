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
package tellier.es.dsl.query.builder.filter;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Represents a Limit filter
 * 
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-limit-filter.html
 */
public class DSLLimitFilter implements DSLFilter {
    
    private static final String LIMIT = "limit";
    private static final String VALUE = "value";
    
    private long value;

    public DSLLimitFilter(long value) {
        this.value = value;
    }
    
    public JsonObject getFilterAsJson() {
        JsonObject result = new JsonObject();
        JsonObject limitObject = new JsonObject();
        result.add(LIMIT, limitObject);
        limitObject.add(VALUE, new JsonPrimitive(value));
        return result;
    }
}
