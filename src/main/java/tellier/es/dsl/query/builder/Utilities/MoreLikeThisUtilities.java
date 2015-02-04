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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.List;

/**
 * Utilities for More Like This query and More Like This Field query
 */
public class MoreLikeThisUtilities {

    private final static String LIKE_TEXT = "like_text";
    private final static String PERCENT_TERMS_TO_MATCH = "percent_terms_to_match";
    private final static String MIN_TERM_FREQ = "min_term_freq";
    private final static String MAX_QUERY_TERMS = "max_query_terms";
    private final static String STOP_WORDS = "stop_words";
    private final static String MIN_DOC_FREQ = "min_doc_freq";
    private final static String MAX_DOC_FREQ = "max_doc_freq";
    private final static String MIN_WORD_LENGTH = "min_word_length";
    private final static String MAX_WORD_LENGTH = "max_word_length";
    private final static String BOOST_TERMS = "boost_terms";

    private String likeText;
    private Double percentTermsToMatch;
    private Integer minTermFreq;
    private Integer maxQueryTerms;
    private List<String> stopWords = new ArrayList<String>();
    private Integer minDocFreq;
    private Integer maxDocFreq;
    private Integer minWordLength;
    private Integer maxWordLength;
    private Integer boostTerms;

    public void setLikeText(String likeText) {
        this.likeText = likeText;
    }

    public void setPercentTermsToMatch(Double percentTermsToMatch) {
        this.percentTermsToMatch = percentTermsToMatch;
    }

    public void setMinTermFreq(Integer minTermFreq) {
        this.minTermFreq = minTermFreq;
    }

    public void setMaxQueryTerms(Integer maxQueryTerms) {
        this.maxQueryTerms = maxQueryTerms;
    }

    public void setMinDocFreq(Integer minDocFreq) {
        this.minDocFreq = minDocFreq;
    }

    public void setMaxDocFreq(Integer maxDocFreq) {
        this.maxDocFreq = maxDocFreq;
    }

    public void setMinWordLength(Integer minWordLength) {
        this.minWordLength = minWordLength;
    }

    public void setMaxWordLength(Integer maxWordLength) {
        this.maxWordLength = maxWordLength;
    }

    public void setBoostTerms(Integer boostTerms) {
        this.boostTerms = boostTerms;
    }

    public void addStopWord(String stopWord) {
        stopWords.add(stopWord);
    }

    public JsonObject fillJsonObject(JsonObject object) {
        if (likeText != null) {
            object.add(LIKE_TEXT, new JsonPrimitive(likeText));
        }
        if (percentTermsToMatch != null) {
            object.add(PERCENT_TERMS_TO_MATCH, new JsonPrimitive(percentTermsToMatch));
        }
        if (minTermFreq != null) {
            object.add(MIN_TERM_FREQ, new JsonPrimitive(minTermFreq));
        }
        if (maxQueryTerms != null) {
            object.add(MAX_QUERY_TERMS, new JsonPrimitive(maxQueryTerms));
        }
        if (stopWords.size()> 0) {
            JsonArray stopWordsArray = new JsonArray();
            for(String stopWord : stopWords) {
                stopWordsArray.add(new JsonPrimitive(stopWord));
            }
            object.add(STOP_WORDS, stopWordsArray);
        }
        if (minDocFreq != null) {
            object.add(MIN_DOC_FREQ, new JsonPrimitive(minDocFreq));
        }
        if (maxDocFreq != null) {
            object.add(MAX_DOC_FREQ, new JsonPrimitive(maxDocFreq));
        }
        if (minWordLength != null) {
            object.add(MIN_WORD_LENGTH, new JsonPrimitive(minWordLength));
        }
        if(maxWordLength != null) {
            object.add(MAX_WORD_LENGTH, new JsonPrimitive(maxWordLength));
        }
        if(boostTerms != null) {
            object.add(BOOST_TERMS, new JsonPrimitive(boostTerms));
        }
        return object;
    }
}
