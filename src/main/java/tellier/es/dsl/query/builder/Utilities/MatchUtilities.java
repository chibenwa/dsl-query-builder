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
package tellier.es.dsl.query.builder.Utilities;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Code factorisation purpose
 */
public class MatchUtilities {

    private static final  String OPERATOR = "operator";
    private static final  String AND = "and";
    private static final  String MINIMUM_SHOULD_MATCH = "minimum_should_match";
    private static final  String ANALYZER = "analyzer";
    private static final  String CUT_OFF_FREQUENCY = "cutoff_frequency";
    private static final  String MAX_EXPANSIONS = "max_expansions";
    private static final  String ZERO_TERMS_QUERY = "zero_terms_query";
    private static final  String ALL = "all";
    private static final  String FUZZINESS = "fuzziness";
    private static final  String BOOST = "boost";
    private static final  String TIE_BREAKER = "tie_breaker";

    private String analyser;
    private Double cutoff_frequency;
    private String minimumShouldMatch;
    private Operator operator;
    private Integer fuzziness;
    private Long max_expansions;
    private Zero_Terms_Query zero_terms_query;
    private DSLMinimumShouldMatch minimumShouldMatchObject;
    private Double boost;
    private Double tieBraker;


    public MatchUtilities() {
        this.operator = Operator.OR;
        this.zero_terms_query = Zero_Terms_Query.NONE;
    }

    public enum Zero_Terms_Query {
        NONE,
        ALL
    }

    public enum Operator {
        AND,
        OR
    }

    public String getAnalyser() {
        return analyser;
    }

    public void setAnalyser(String analyser) {
        this.analyser = analyser;
    }

    public Double getCutoff_frequency() {
        return cutoff_frequency;
    }

    public void setCutoff_frequency(Double cutoff_frequency) {
        this.cutoff_frequency = cutoff_frequency;
    }

    public String getMinimumShouldMatch() {
        return minimumShouldMatch;
    }

    public void setMinimumShouldMatch(String minimumShouldMatch) {
        this.minimumShouldMatch = minimumShouldMatch;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Integer getFuzziness() {
        return fuzziness;
    }

    public void setFuzziness(Integer fuzziness) {
        this.fuzziness = fuzziness;
    }

    public Long getMax_expansions() {
        return max_expansions;
    }

    public void setMax_expansions(Long max_expansions) {
        this.max_expansions = max_expansions;
    }

    public Zero_Terms_Query getZero_terms_query() {
        return zero_terms_query;
    }

    public void setZero_terms_query(Zero_Terms_Query zero_terms_query) {
        this.zero_terms_query = zero_terms_query;
    }

    public void setBoost(Double boost) {
        this.boost = boost;
    }

    public void setTieBraker(Double tieBraker) {
        this.tieBraker = tieBraker;
    }

    public boolean isEmpty() {
        return (analyser == null && max_expansions == null && cutoff_frequency == null && operator == Operator.OR && minimumShouldMatch == null && zero_terms_query == Zero_Terms_Query.NONE && fuzziness == null && minimumShouldMatchObject == null && boost == null && tieBraker == null );
    }

    public void setMinimumShouldMatch(DSLMinimumShouldMatch dslMinimumShouldMatchObject) {
        this.minimumShouldMatchObject = dslMinimumShouldMatchObject;
    }

    public void applyMatchUtilitiesOnJson(JsonObject queryJson) {
        if(operator == Operator.AND) {
            queryJson.add(OPERATOR, new JsonPrimitive(AND) );
        }
        if(minimumShouldMatchObject != null) {
            queryJson.add(MINIMUM_SHOULD_MATCH, minimumShouldMatchObject.getParameterAsJson());
        } else {
            if(minimumShouldMatch != null) {
                queryJson.add(MINIMUM_SHOULD_MATCH, new JsonPrimitive(minimumShouldMatch) );
            }
        }
        if(analyser != null) {
            queryJson.add(ANALYZER, new JsonPrimitive(analyser));
        }
        if(cutoff_frequency != null) {
            queryJson.add(CUT_OFF_FREQUENCY, new JsonPrimitive(cutoff_frequency));
        }
        if(zero_terms_query == Zero_Terms_Query.ALL) {
            queryJson.add(ZERO_TERMS_QUERY, new JsonPrimitive(ALL));
        }
        if(fuzziness != null) {
            queryJson.add(FUZZINESS, new JsonPrimitive(fuzziness));
        }
        if(max_expansions != null) {
            queryJson.add(MAX_EXPANSIONS, new JsonPrimitive(max_expansions));
        }
        if(boost != null) {
            queryJson.add(BOOST, new JsonPrimitive(boost));
        }
        if(tieBraker != null) {
            queryJson.add(TIE_BREAKER, new JsonPrimitive(tieBraker));
        }
    }
}
