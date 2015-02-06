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
import tellier.es.dsl.query.builder.Utilities.DSLRange;

/**
 * Represents a RangeFilter
 * 
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-range-filter.html
 */
public class DSLRangeFilter implements DSLFilter{
    private DSLRange dslRange;

    public DSLRangeFilter(String field) {
        dslRange = new DSLRange(field);
    }

    public DSLRangeFilter gte(Number value) {
        dslRange.gte(value);
        return this;
    }

    public DSLRangeFilter lte(Number value) {
        dslRange.lte(value);
        return this;
    }

    public DSLRangeFilter gt(Number value) {
        dslRange.gt(value);
        return this;
    }

    public DSLRangeFilter lt(Number value) {
        dslRange.lt(value);
        return this;
    }

    public DSLRangeFilter gte(String string) {
        dslRange.gte(string);
        return this;
    }

    public DSLRangeFilter lte(String string) {
        dslRange.lte(string);
        return this;
    }

    public DSLRangeFilter gt(String string) {
        dslRange.gt(string);
        return this;
    }

    public DSLRangeFilter lt(String string) {
        dslRange.lt(string);
        return this;
    }

    public JsonObject getFilterAsJson() {
        return dslRange.getAsJson();
    }

}
