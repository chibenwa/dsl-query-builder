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

/**
 * Represents Span Not query
 *
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-span-not-query.html
 */
public class DSLSpanNotQuery implements DSLSpanQuery {

    private static final String SPAN_NOT = "span_not";
    private static final String INCLUDE = "include";
    private static final String EXCLUDE = "exclude";

    private DSLSpanQuery includeSpanQuery;
    private DSLSpanQuery excludeSpanQuery;

    public DSLSpanNotQuery(DSLSpanQuery includeSpanQuery, DSLSpanQuery excludeSpanQuery) {
        this.includeSpanQuery = includeSpanQuery;
        this.excludeSpanQuery = excludeSpanQuery;
    }

    public JsonObject getQueryAsJson() {
        JsonObject result = new JsonObject();
        JsonObject spanNotObject = new JsonObject();
        result.add(SPAN_NOT, spanNotObject);
        spanNotObject.add(INCLUDE, includeSpanQuery.getQueryAsJson());
        spanNotObject.add(EXCLUDE, excludeSpanQuery.getQueryAsJson());
        return result;
    }

}
