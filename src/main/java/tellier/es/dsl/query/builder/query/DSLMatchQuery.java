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
 * Represents a query getting documents matching a search criterion in a given field.
 */
public class DSLMatchQuery implements DSLQuery{
    public final String MATCH = "match";
    public final String OPERATOR = "operator";
    public final String AND = "and";
    public final String MINIMUM_SHOULD_MATCH = "minimum_should_match";
    public final String ANALYZER = "analyzer";
    public final String LENIENT = "lenient";
    public final String CUT_OFF_FREQUENCY = "cutoff_frequency";
    public final String TYPE = "type";
    public final String SLOP = "slop";
    public final String MAX_EXPANSIONS = "max_expansions";
    public final String ZERO_TERMS_QUERY = "zero_terms_query";
    public final String ALL = "all";
    public final String PHRASE = "phrase";
    public final String PHRASE_PREFIX = "phrase_prefix";

    private String field;
    private String searchCriterion;
    private Operator operator;
    private Type type;
    private String analyser; // Not used when type MATCH
    private Long max_expansions; // Only used for MATCH_PHRASE_PREFIX
    private boolean lenient;
    private Double cutoff_frequency;
    private String minimumShouldMatch;
    private Integer slop; // Only used by MATCH_PHRASE and MATCH_PHRASE_PREFIX
    private Zero_Terms_Query zero_terms_query;

    enum Zero_Terms_Query {
        NONE,
        ALL
    }

    /**
     * @param field The document field you want to search into
     * @param searchCriterion The pattern you want to search
     */
    public DSLMatchQuery(String field, String searchCriterion) {
        this.field = field;
        this.searchCriterion = searchCriterion;
        this.operator = Operator.OR;
        this.type = Type.MATCH;
        this.lenient = false;
        this.zero_terms_query = Zero_Terms_Query.NONE;
        }

    public DSLMatchQuery setOperator(Operator operator) {
        this.operator = operator;
        return this;
    }

    public DSLMatchQuery setMinimumShouldMatch(String minimumShouldMatch) {
        this.minimumShouldMatch = minimumShouldMatch;
        return this;
    }

    public DSLMatchQuery setType(Type type) {
        this.type = type;
        return this;
    }

    public DSLMatchQuery setAnalyser(String analyser) {
        this.analyser = analyser;
        return this;
    }

    public DSLMatchQuery setLenient(boolean lenient) {
        this.lenient = lenient;
        return this;
    }

    public DSLMatchQuery setMaxExpansion(Long max_expansions) {
        this.max_expansions = max_expansions;
        return this;
    }

    public DSLMatchQuery setCutoff_Frequency(Double cutoff_frequency) {
        this.cutoff_frequency = cutoff_frequency;
        return this;
    }

    public DSLMatchQuery setSlop(Integer slop) {
        this.slop = slop;
        return this;
    }

    public DSLMatchQuery setZroTermsQuery(Zero_Terms_Query zero_terms_query) {
        this.zero_terms_query = zero_terms_query;
        return this;
    }

    enum Operator {
        AND,
        OR
    }

    enum Type {
        MATCH,
        MATCH_PHRASE,
        MATCH_PHRASE_PREFIX
    }

    public JsonObject getQueryAsJson() {
        if(field.isEmpty() || searchCriterion.isEmpty()) {
            return null;
        }
        if(analyser == null && !lenient && max_expansions == null && cutoff_frequency == null && type == Type.MATCH && operator == Operator.OR && minimumShouldMatch == null && zero_terms_query == Zero_Terms_Query.NONE) {
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
        if(operator == Operator.AND) {
            queryJson.add(OPERATOR, new JsonPrimitive(AND) );
        }
        if(minimumShouldMatch != null) {
            queryJson.add(MINIMUM_SHOULD_MATCH, new JsonPrimitive(minimumShouldMatch) );
        }
        if(analyser != null) {
            queryJson.add(ANALYZER, new JsonPrimitive(analyser));
        }
        if(type != Type.MATCH) {
            switch (type) {
                case MATCH_PHRASE:
                    queryJson.add(TYPE, new JsonPrimitive(PHRASE));
                    break;
                case MATCH_PHRASE_PREFIX:
                    queryJson.add(TYPE, new JsonPrimitive(PHRASE_PREFIX));
                    if(max_expansions != null) {
                        queryJson.add(MAX_EXPANSIONS, new JsonPrimitive(max_expansions));
                    }
                    break;
            }
            if(slop != null) {
                queryJson.add(SLOP, new JsonPrimitive(slop));
            }
        }
        if(lenient) {
            queryJson.add(LENIENT, new JsonPrimitive(true));
        }
        if(cutoff_frequency != null) {
            queryJson.add(CUT_OFF_FREQUENCY, new JsonPrimitive(cutoff_frequency));
        }
        if(zero_terms_query == Zero_Terms_Query.ALL) {
            queryJson.add(ZERO_TERMS_QUERY, new JsonPrimitive(ALL));
        }
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