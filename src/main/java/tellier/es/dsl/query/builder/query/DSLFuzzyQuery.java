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
import tellier.es.dsl.query.builder.Utilities.FuzzyQueriesUtilities;
import tellier.es.dsl.query.builder.Utilities.MatchUtilities;

/**
 * Represents a DSLFuzzyQuery
 *
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-fuzzy-query.html
 */
public class DSLFuzzyQuery implements DSLMultiTermQuery {

    private static final  String FUZZY = "fuzzy";
    private static final  String VALUE = "value";
    private static final  String MAX_EXPANSIONS = "max_expansions";

    private String field;
    private String value;
    private Integer max_expansions;
    private MatchUtilities matchUtilities;
    private FuzzyQueriesUtilities fuzzyQueriesUtilities;

    public DSLFuzzyQuery(String field, String value) {
        this.field = field;
        this.value = value;
        this.matchUtilities = new MatchUtilities();
        this.fuzzyQueriesUtilities = new FuzzyQueriesUtilities();
    }

    public DSLFuzzyQuery setMax_expansions(Integer max_expansions) {
        this.max_expansions = max_expansions;
        return this;
    }

    public DSLFuzzyQuery setFuzziness(Integer fuzziness) {
        matchUtilities.setFuzziness(fuzziness);
        return this;
    }

    public DSLFuzzyQuery setBoost(Double boost) {
        matchUtilities.setBoost(boost);
        return this;
    }

    public DSLFuzzyQuery setPrefix_length(Integer prefix_length) {
        fuzzyQueriesUtilities.setPrefix_length(prefix_length);
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject fieldBody = new JsonObject();
        fieldBody.add(VALUE, new JsonPrimitive(value));
        fuzzyQueriesUtilities.applyMatchUtilitiesOnJson(fieldBody);
        matchUtilities.applyMatchUtilitiesOnJson(fieldBody);
        if(max_expansions != null) {
            fieldBody.add(MAX_EXPANSIONS, new JsonPrimitive(max_expansions));
        }
        JsonObject fuzzyBody = new JsonObject();
        fuzzyBody.add(field, fieldBody);
        JsonObject result = new JsonObject();
        result.add(FUZZY, fuzzyBody);
        return result;
    }
}
