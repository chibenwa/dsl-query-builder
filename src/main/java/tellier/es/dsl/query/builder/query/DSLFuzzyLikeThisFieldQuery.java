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
import tellier.es.dsl.query.builder.Utilities.FuzzyQueriesUtilities;
import tellier.es.dsl.query.builder.Utilities.MatchUtilities;

/**
 * Represents a Fuzzy Like This Field Query
 *
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-flt-field-query.html
 */
public class DSLFuzzyLikeThisFieldQuery implements DSLQuery {

    private static final  String FLT_FIELD = "flt_field";

    private FuzzyQueriesUtilities fuzzyQueriesUtilities;
    private MatchUtilities matchUtilities;
    private String field;

    public DSLFuzzyLikeThisFieldQuery(String like_text, String field) {
        fuzzyQueriesUtilities = new FuzzyQueriesUtilities();
        matchUtilities = new MatchUtilities();
        fuzzyQueriesUtilities.setLike_text(like_text);
        this.field = field;
    }

    public DSLFuzzyLikeThisFieldQuery setBoost(Double boost) {
        matchUtilities.setBoost(boost);
        return this;
    }

    public DSLFuzzyLikeThisFieldQuery setFuzziness(Integer fuzziness) {
        matchUtilities.setFuzziness(fuzziness);
        return this;
    }

    public DSLFuzzyLikeThisFieldQuery setAnalyser(String analyser) {
        matchUtilities.setAnalyser(analyser);
        return this;
    }

    public DSLFuzzyLikeThisFieldQuery setIgnore_tf(Boolean ignore_tf) {
        fuzzyQueriesUtilities.setIgnore_tf(ignore_tf);
        return this;
    }

    public DSLFuzzyLikeThisFieldQuery setMaxQueryTerms(Integer maxQueryTerms) {
        fuzzyQueriesUtilities.setMax_query_terms(maxQueryTerms);
        return this;
    }

    public DSLFuzzyLikeThisFieldQuery setPrefixLength(Integer prefixLength) {
        fuzzyQueriesUtilities.setPrefix_length(prefixLength);
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject fieldBody = new JsonObject();
        fuzzyQueriesUtilities.applyMatchUtilitiesOnJson(fieldBody);
        matchUtilities.applyMatchUtilitiesOnJson(fieldBody);
        JsonObject fuzzyBody = new JsonObject();
        fuzzyBody.add(field, fieldBody);
        JsonObject result = new JsonObject();
        result.add(FLT_FIELD, fuzzyBody);
        return result;
    }

}
