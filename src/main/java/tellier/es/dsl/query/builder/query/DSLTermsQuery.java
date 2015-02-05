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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Terms query
 *
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-terms-query.html
 */
public class DSLTermsQuery implements DSLQuery {

    private static final String TERMS = "terms";
    private static final String MINIMUM_SHOULD_MATCH = "minimum_should_match";

    private String field;
    private List<String> values = new ArrayList<String>();
    private Integer minimumShouldMatch;

    public DSLTermsQuery(String field, Integer minimumShouldMatch) {
        this.field = field;
        this.minimumShouldMatch = minimumShouldMatch;
    }

    public DSLTermsQuery addTerm(String value) {
        values.add(value);
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject result = new JsonObject();
        JsonObject termsObject = new JsonObject();
        result.add(TERMS, termsObject);
        JsonArray valuesArray = new JsonArray();
        for(String value : values) {
            valuesArray.add(new JsonPrimitive(value));
        }
        termsObject.add(field, valuesArray);
        termsObject.add(MINIMUM_SHOULD_MATCH, new JsonPrimitive(minimumShouldMatch));
        return result;
    }
}
