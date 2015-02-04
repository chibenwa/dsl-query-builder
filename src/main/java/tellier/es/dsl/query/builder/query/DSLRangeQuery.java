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
package tellier.es.dsl.query.builder.query;

import com.google.gson.JsonObject;
import tellier.es.dsl.query.builder.Utilities.DSLRange;

/**
 * Represents a Range Query
 *
 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-range-query.html
 */
public class DSLRangeQuery {

    private DSLRange dslRange;
    private static final String BOOST = "boost";
    private Double boost;

    public DSLRangeQuery(String field) {
        dslRange = new DSLRange(field);
    }

    public DSLRangeQuery gte(Number value) {
        dslRange.gte(value);
        return this;
    }

    public DSLRangeQuery lte(Number value) {
        dslRange.lte(value);
        return this;
    }

    public DSLRangeQuery gt(Number value) {
        dslRange.gt(value);
        return this;
    }

    public DSLRangeQuery lt(Number value) {
        dslRange.lt(value);
        return this;
    }

    public DSLRangeQuery gte(String string) {
        dslRange.gte(string);
        return this;
    }

    public DSLRangeQuery lte(String string) {
        dslRange.lte(string);
        return this;
    }

    public DSLRangeQuery gt(String string) {
        dslRange.gt(string);
        return this;
    }

    public DSLRangeQuery lt(String string) {
        dslRange.lt(string);
        return this;
    }

    public DSLRangeQuery setBoost(Double boost) {
        dslRange.setBoost(boost);
        return this;
    }

    public DSLRangeQuery setTimeZone(String timeZone) {
        dslRange.setTimeZone(timeZone);
        return this;
    }

    public JsonObject getQueryAsJson() {
        return dslRange.getAsJson();
    }

}
