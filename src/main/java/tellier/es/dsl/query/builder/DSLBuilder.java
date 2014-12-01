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
package tellier.es.dsl.query.builder;

import com.google.gson.JsonObject;
import tellier.es.dsl.query.builder.filter.DSLFilter;
import tellier.es.dsl.query.builder.query.DSLFilteredQuery;
import tellier.es.dsl.query.builder.query.DSLMatchAllQuery;
import tellier.es.dsl.query.builder.query.DSLQuery;

/**
 * A DSL builder gives you the JSON you should use to perform your search
 */
public class DSLBuilder {

    private DSLFilter filter;
    private DSLQuery query;

    public DSLBuilder setFilter(DSLFilter filter) {
        this.filter = filter;
        return this;
    }

    public DSLBuilder setQuery(DSLQuery query) {
        this.query = query;
        return this;
    }

    public JsonObject build() {
        if(filter == null) {
            if(query == null) {
                return null;
            }
            return buildQuery();
        }
        if(query == null) {
            query = new DSLMatchAllQuery();
        }
        return combineQueryAndFilter();
    }

    private JsonObject buildQuery() {
        JsonObject queryObject = new JsonObject();
        queryObject.add(DSLQuery.QUERY, query.getQueryAsJson());
        return queryObject;
    }

    private JsonObject combineQueryAndFilter() {
        JsonObject body = new DSLFilteredQuery(query, filter).getQueryAsJson();
        JsonObject finalQuery = new JsonObject();
        finalQuery.add(DSLQuery.QUERY, body);
        return finalQuery;
    }
}
