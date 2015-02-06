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
 * Prefix filter
 * 
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-prefix-filter.html
 */
public class DSLPrefixFilter implements DSLFilter {
    
    private static final String PREFIX = "prefix";
    
    private String field;
    private String prefix;
    private Boolean cache;

    public DSLPrefixFilter(String field, String prefix) {
        this.field = field;
        this.prefix = prefix;
    }

    public DSLPrefixFilter setCache(Boolean cache) {
        this.cache = cache;
        return this;
    }

    public JsonObject getFilterAsJson() {
        JsonObject result = new JsonObject();
        JsonObject prefixObject = new JsonObject();
        result.add(PREFIX, prefixObject);
        prefixObject.add(field, new JsonPrimitive(prefix));
        if(cache != null) {
            prefixObject.add(CACHE, new JsonPrimitive(cache));
        }
        return result;
    }
}
