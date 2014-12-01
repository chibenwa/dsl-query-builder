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

/**
 * A DSLQuery is an object that represent a DSL filter.
 *
 * The basic operation is to get it as a JSON.
 */
public interface DSLFilter {
    public final String AND = "and";
    public final String OR = "or";
    public final String NOT = "not";
    public final String TERM = "term";
    public final String CACHE = "_cache";
    public final String GTE = "gte";
    public final String LTE = "lte";
    public final String GT = "gt";
    public final String LT = "lt";
    public final String RANGE = "range";
    public final String NESTED = "nested";
    public final String PATH = "path";
    public final String FILTER = "filter";

    /**
     *
     * @return JsonObject that will perform this filter
     */
    public JsonObject getQueryAsJson();
}
