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
import tellier.es.dsl.query.builder.Utilities.DSLMinimumShouldMatch;
import tellier.es.dsl.query.builder.Utilities.MatchUtilities;

/**
 * A DSL common query
 *
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-common-terms-query.html for details.
 */
public class DSLCommonQuery implements DSLQuery {

    private static final  String COMMON = "common";
    private static final  String BODY = "body";
    private static final  String AND = "and";
    private static final  String LOW_FREQ_OPERATOR = "low_freq_operator";
    private static final  String HIGH_FREQ_OPERATOR = "high_freq_operator";

    private String query;
    private MatchUtilities matchUtilities;
    private Freq_operator low_freq_operator;
    private Freq_operator high_freq_operator;


    public DSLCommonQuery(String query) {
        this.query = query;
        this.matchUtilities = new MatchUtilities();
        this.low_freq_operator = Freq_operator.OR;
        this.high_freq_operator = Freq_operator.OR;
    }

    public DSLCommonQuery setCutoffFrequency(Double d) {
        matchUtilities.setCutoff_frequency(d);
        return this;
    }

    enum Freq_operator {
        AND,
        OR
    }

    public DSLCommonQuery setLowFrequencyOperator(Freq_operator operator) {
        this.low_freq_operator = operator;
        return this;
    }

    public DSLCommonQuery setHighFrequencyOperator(Freq_operator operator) {
        this.high_freq_operator = operator;
        return this;
    }

    public DSLCommonQuery setMinimumShouldMatch(String minimumShouldMatch) {
        this.matchUtilities.setMinimumShouldMatch(minimumShouldMatch);
        return this;
    }

    public DSLCommonQuery setMinimumShouldMatch(DSLMinimumShouldMatch minimumShouldMatch) {
        this.matchUtilities.setMinimumShouldMatch(minimumShouldMatch);
        return this;
    }

    public DSLCommonQuery setAnalyzer(String analyzer) {
        this.matchUtilities.setAnalyser(analyzer);
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject jsonContent = new JsonObject();
        jsonContent.add(QUERY, new JsonPrimitive(query));
        matchUtilities.applyMatchUtilitiesOnJson(jsonContent);
        if(low_freq_operator == Freq_operator.AND) {
            jsonContent.add(LOW_FREQ_OPERATOR, new JsonPrimitive(AND));
        }
        if(high_freq_operator == Freq_operator.AND) {
            jsonContent.add(HIGH_FREQ_OPERATOR, new JsonPrimitive(AND));
        }
        JsonObject body = new JsonObject();
        body.add(BODY, jsonContent);
        JsonObject result = new JsonObject();
        result.add(COMMON, body);
        return result;
    }

    // TODO BOOST support, disable_coord support
}
