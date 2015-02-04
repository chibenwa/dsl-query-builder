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
import tellier.es.dsl.query.builder.Utilities.MatchUtilities;
import tellier.es.dsl.query.builder.Utilities.MoreLikeThisUtilities;

/**
 * Represents a more like this field query
 *
 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-mlt-field-query.html
 */
public class DSLMoreLikeThisFieldQuery implements DSLQuery {

    private static final String MORE_LIKE_THIS_FIELD = "mlt_field";

    private String field;
    private MatchUtilities matchUtilities = new MatchUtilities();
    private MoreLikeThisUtilities moreLikeThisUtilities = new MoreLikeThisUtilities();

    public DSLMoreLikeThisFieldQuery(String field) {
        this.field = field;
    }

    public JsonObject getQueryAsJson() {
        JsonObject result = new JsonObject();
        JsonObject moreLikeThisFieldObject = new JsonObject();
        JsonObject fieldObject = new JsonObject();
        result.add(MORE_LIKE_THIS_FIELD, moreLikeThisFieldObject);
        fieldObject = moreLikeThisUtilities.fillJsonObject(fieldObject);
        matchUtilities.applyMatchUtilitiesOnJson(fieldObject);
        moreLikeThisFieldObject.add(field, fieldObject);
        return result;
    }

    public DSLMoreLikeThisFieldQuery setAnalyser(String analyser) {
        matchUtilities.setAnalyser(analyser);
        return this;
    }

    public DSLMoreLikeThisFieldQuery setBoost(Double boost) {
        matchUtilities.setBoost(boost);
        return this;
    }

    public DSLMoreLikeThisFieldQuery setLikeText(String likeText) {
        moreLikeThisUtilities.setLikeText(likeText);
        return this;
    }

    public DSLMoreLikeThisFieldQuery setPercentTermsToMatch(Double percentTermsToMatch) {
        moreLikeThisUtilities.setPercentTermsToMatch(percentTermsToMatch);
        return this;
    }

    public DSLMoreLikeThisFieldQuery setMinTermFreq(Integer minTermFreq) {
        moreLikeThisUtilities.setMinTermFreq(minTermFreq);
        return this;
    }

    public DSLMoreLikeThisFieldQuery setMaxQueryTerms(Integer maxQueryTerms) {
        moreLikeThisUtilities.setMaxQueryTerms(maxQueryTerms);
        return this;
    }

    public DSLMoreLikeThisFieldQuery setMinDocFreq(Integer minDocFreq) {
        moreLikeThisUtilities.setMinDocFreq(minDocFreq);
        return this;
    }

    public DSLMoreLikeThisFieldQuery setMaxDocFreq(Integer maxDocFreq) {
        moreLikeThisUtilities.setMaxDocFreq(maxDocFreq);
        return this;
    }

    public DSLMoreLikeThisFieldQuery setMinWordLength(Integer minWordLength) {
        moreLikeThisUtilities.setMinWordLength(minWordLength);
        return this;
    }

    public DSLMoreLikeThisFieldQuery setMaxWordLength(Integer maxWordLength) {
        moreLikeThisUtilities.setMaxWordLength(maxWordLength);
        return this;
    }

    public DSLMoreLikeThisFieldQuery setBoostTerms(Integer boostTerms) {
        moreLikeThisUtilities.setBoostTerms(boostTerms);
        return this;
    }

    public DSLMoreLikeThisFieldQuery addStopWord(String stopWord) {
        moreLikeThisUtilities.addStopWord(stopWord);
        return this;
    }

}
