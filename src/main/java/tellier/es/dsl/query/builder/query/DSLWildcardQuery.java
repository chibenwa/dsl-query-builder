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
 * Represents a Wildcard query
 *
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-wildcard-query.html
 */
public class DSLWildcardQuery implements DSLQuery {

    private static final String WILDCARD = "wildcard";
    private static final String BOOST = "boost";

    private String field;
    private String value;
    private Double boost;

    public DSLWildcardQuery(String field, String value) {
        this.field = field;
        this.value = value;
    }

    public DSLWildcardQuery setBoost(Double boost) {
        this.boost = boost;
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject result = new JsonObject();
        JsonObject wildcardObject = new JsonObject();
        result.add(WILDCARD, wildcardObject);
        if(boost == null) {
            wildcardObject.add(field, new JsonPrimitive(value));
        } else {
            JsonObject innerObject = new JsonObject();
            innerObject.add(WILDCARD, new JsonPrimitive(value));
            innerObject.add(BOOST, new JsonPrimitive(boost));
            wildcardObject.add(field, innerObject);
        }
        return result;
    }
}
