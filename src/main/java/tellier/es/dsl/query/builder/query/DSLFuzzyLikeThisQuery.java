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
import tellier.es.dsl.query.builder.Utilities.FuzzyQueriesUtilities;
import tellier.es.dsl.query.builder.Utilities.MatchUtilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Fuzzy Like This Query
 *
 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-flt-query.html
 */
public class DSLFuzzyLikeThisQuery implements DSLQuery {

    private static final  String FLT = "flt";
    private static final  String FIELDS = "fields";

    private MatchUtilities matchUtilities;
    private FuzzyQueriesUtilities fuzzyQueriesUtilities;
    private List<String> fields;

    public DSLFuzzyLikeThisQuery(String like_text) {
        fields = new ArrayList<String>();
        matchUtilities = new MatchUtilities();
        fuzzyQueriesUtilities = new FuzzyQueriesUtilities();
        fuzzyQueriesUtilities.setLike_text(like_text);
    }

    public DSLFuzzyLikeThisQuery setBoost(Double boost) {
        matchUtilities.setBoost(boost);
        return this;
    }

    public DSLFuzzyLikeThisQuery setFuzziness(Integer fuzziness) {
        matchUtilities.setFuzziness(fuzziness);
        return this;
    }

    public DSLFuzzyLikeThisQuery setAnalyser(String analyser) {
        matchUtilities.setAnalyser(analyser);
        return this;
    }

    public DSLFuzzyLikeThisQuery addField(String field) {
        fields.add(field);
        return this;
    }

    public DSLFuzzyLikeThisQuery setIgnore_tf(Boolean ignore_tf) {
        fuzzyQueriesUtilities.setIgnore_tf(ignore_tf);
        return this;
    }

    public DSLFuzzyLikeThisQuery setMaxQueryTerms(Integer maxQueryTerms) {
        fuzzyQueriesUtilities.setMax_query_terms(maxQueryTerms);
        return this;
    }

    public DSLFuzzyLikeThisQuery setPrefixLength(Integer prefixLength) {
        fuzzyQueriesUtilities.setPrefix_length(prefixLength);
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonArray fieldsArray = new JsonArray();
        for(String field : fields) {
            fieldsArray.add(new JsonPrimitive(field));
        }
        JsonObject fuzzyBody = new JsonObject();
        fuzzyBody.add(FIELDS, fieldsArray);
        fuzzyQueriesUtilities.applyMatchUtilitiesOnJson(fuzzyBody);
        matchUtilities.applyMatchUtilitiesOnJson(fuzzyBody);
        JsonObject result = new JsonObject();
        result.add(FLT, fuzzyBody);
        return result;
    }

}
