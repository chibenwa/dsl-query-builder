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
import tellier.es.dsl.query.builder.filter.DSLFilter;

/**
 * Represents a filtered query.
 * 
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-filtered-query.html
 */
public class DSLFilteredQuery implements DSLQuery {
    private static final  String FILTER = "filter";
    private static final  String FILTERED = "filtered";

    private DSLFilter filter;
    private DSLQuery query;

    public DSLFilteredQuery(DSLQuery query, DSLFilter filter) {
        this.filter = filter;
        this.query = query;
    }

    public DSLFilter getFilter() {
        return filter;
    }

    public DSLFilteredQuery setFilter(DSLFilter filter) {
        this.filter = filter;
        return this;
    }

    public DSLQuery getQuery() {
        return query;
    }

    public DSLFilteredQuery setQuery(DSLQuery query) {
        this.query = query;
        return this;
    }

    public JsonObject getQueryAsJson() {
        if(filter == null) {
            if(query == null) {
                return null;
            }
            return query.getQueryAsJson();
        }
        if(query == null) {
            query = new DSLMatchAllQuery();
        }
        return combineQueryAndFilter();
    }



    private JsonObject combineQueryAndFilter() {
        JsonObject body = new JsonObject();
        body.add(QUERY, query.getQueryAsJson());
        body.add(FILTER, filter.getFilterAsJson());
        JsonObject filteredObject = new JsonObject();
        filteredObject.add(FILTERED, body);
        return filteredObject;
    }
}
