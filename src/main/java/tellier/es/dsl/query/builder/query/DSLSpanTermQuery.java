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
import com.google.gson.JsonPrimitive;

/**
 * DSL span term query
 *
 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-span-term-query.html
 */
public class DSLSpanTermQuery implements DSLSpanQuery {

    private static final String SPAN_TERM = "span-term";
    private static final String BOOST = "boost";
    private static final String VALUE = "value";

    private String field;
    private String value;
    private Double boost;

    public DSLSpanTermQuery(String field, String value) {
        this.field = field;
        this.value = value;
    }

    public DSLSpanTermQuery setBoost(Double boost) {
        this.boost = boost;
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject result = new JsonObject();
        JsonObject spanTermObject = new JsonObject();
        result.add(SPAN_TERM, spanTermObject);
        if(boost != null) {
            JsonObject fieldObject = new JsonObject();
            fieldObject.add(VALUE, new JsonPrimitive(value));
            fieldObject.add(BOOST, new JsonPrimitive(boost));
            spanTermObject.add(field, fieldObject);
        } else {
            spanTermObject.add(field, new JsonPrimitive(value));
        }
        return result;
    }
}
