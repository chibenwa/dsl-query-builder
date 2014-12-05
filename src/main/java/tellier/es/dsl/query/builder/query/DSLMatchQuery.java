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
import tellier.es.dsl.query.builder.Utilities.MatchUtilities;

/**
 * Represents a query getting documents matching a search criterion in a given field.
 */
public class DSLMatchQuery implements DSLQuery{
    public final String MATCH = "match";
    public final String LENIENT = "lenient";
    public final String SLOP = "slop";
    public final String PHRASE = "phrase";
    public final String PHRASE_PREFIX = "phrase_prefix";

    private String field;
    private String searchCriterion;
    private Type type;
    private boolean lenient;
    private Integer slop; // Only used by MATCH_PHRASE and MATCH_PHRASE_PREFIX
    private MatchUtilities matchUtilities;

    /**
     * @param field The document field you want to search into
     * @param searchCriterion The pattern you want to search
     */
    public DSLMatchQuery(String field, String searchCriterion) {
        this.matchUtilities = new MatchUtilities();
        this.field = field;
        this.searchCriterion = searchCriterion;
        this.type = Type.MATCH;
        this.lenient = false;
    }

    public DSLMatchQuery setSlop(Integer slop) {
        this.slop = slop;
        return this;
    }

    public DSLMatchQuery setType(Type type) {
        this.type = type;
        return this;
    }



    public DSLMatchQuery setLenient(boolean lenient) {
        this.lenient = lenient;
        return this;
    }

    public DSLMatchQuery setAnalyser(String analyser) {
        matchUtilities.setAnalyser(analyser);
        return this;
    }

    public DSLMatchQuery setMaxExpansion(Long max_expansions) {
        matchUtilities.setMax_expansions(max_expansions);
        return this;
    }

    public DSLMatchQuery setCutoff_Frequency(Double cutoff_frequency) {
        matchUtilities.setCutoff_frequency(cutoff_frequency);
        return this;
    }

    public DSLMatchQuery setOperator(MatchUtilities.Operator operator) {
        matchUtilities.setOperator(operator);
        return this;
    }

    public DSLMatchQuery setMinimumShouldMatch(String minimumShouldMatch) {
        matchUtilities.setMinimumShouldMatch(minimumShouldMatch);
        return this;
    }

    public DSLMatchQuery setZeroTermsQuery(MatchUtilities.Zero_Terms_Query zero_terms_query) {
        matchUtilities.setZero_terms_query(zero_terms_query);
        return this;
    }

    public DSLMatchQuery setFuzziness(Integer fuzziness) {
        matchUtilities.setFuzziness(fuzziness);
        return this;
    }

    public enum Type {
        MATCH,
        MATCH_PHRASE,
        MATCH_PHRASE_PREFIX
    }

    public JsonObject getQueryAsJson() {
        if(field.isEmpty() || searchCriterion.isEmpty()) {
            return null;
        }
        if(matchUtilities.isEmpty() && type == Type.MATCH && !lenient) {
            return classicQuery();
        } else {
            JsonObject matchJson = new JsonObject();
            JsonObject queryJson = prepareJsonQuery();
            matchJson.add(MATCH, queryJson);
            return matchJson;
        }
    }

    private JsonObject prepareJsonQuery() {
        JsonObject queryJson = new JsonObject();
        queryJson.add(QUERY, new JsonPrimitive(searchCriterion) );
        if(type != Type.MATCH) {
            switch (type) {
                case MATCH_PHRASE:
                    queryJson.add(TYPE, new JsonPrimitive(PHRASE));
                    break;
                case MATCH_PHRASE_PREFIX:
                    queryJson.add(TYPE, new JsonPrimitive(PHRASE_PREFIX));
                    break;
            }
            if(slop != null) {
                queryJson.add(SLOP, new JsonPrimitive(slop));
            }
        }
        if(lenient) {
            queryJson.add(LENIENT, new JsonPrimitive(true));
        }
        matchUtilities.applyMatchUtilitiesOnJson(queryJson);
        JsonObject result = new JsonObject();
        result.add(field, queryJson);
        return result;
    }

    private JsonObject classicQuery() {
        JsonObject matchField = new JsonObject();
        JsonPrimitive searchPrimitive = new JsonPrimitive(searchCriterion);
        matchField.add(field, searchPrimitive);
        JsonObject result = new JsonObject();
        result.add(MATCH, matchField);
        return result;
    }
}