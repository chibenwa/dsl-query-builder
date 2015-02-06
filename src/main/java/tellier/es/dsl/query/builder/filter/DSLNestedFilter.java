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
 * Represents a nested filter
 * 
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-nested-filter.html
 */
public class DSLNestedFilter implements DSLFilter {

    private static final String PATH = "path";
    private static final String FILTER = "filter";
    private static final String NESTED = "nested";
    
    private String path;
    private DSLFilter subFilter;
    private boolean cache;

    public DSLNestedFilter(String path, DSLFilter subFilter) {
        this(path, subFilter, false);
    }

    public DSLNestedFilter(String path, DSLFilter subFilter, boolean cache) {
        this.path = path;
        this.subFilter = subFilter;
        this.cache = cache;
    }

    public String getPath() {
        return path;
    }

    public DSLNestedFilter setPath(String path) {
        this.path = path;
        return this;
    }

    public DSLFilter getSubFilter() {
        return subFilter;
    }

    public DSLNestedFilter setSubFilter(DSLFilter subFilter) {
        this.subFilter = subFilter;
        return this;
    }

    public boolean isCache() {
        return cache;
    }

    public DSLNestedFilter setCache(boolean cache) {
        this.cache = cache;
        return this;
    }

    public JsonObject getFilterAsJson() {
        JsonObject filterJson = new JsonObject();
        filterJson.add(PATH, new JsonPrimitive(path));
        filterJson.add(FILTER, subFilter.getFilterAsJson());
        JsonObject result = new JsonObject();
        result.add(NESTED, filterJson);
        return result;
    }
}
