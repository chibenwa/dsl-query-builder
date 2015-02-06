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
 * Code factorization for DSLFuzzyLikeThisQuery and DSLFuzzyLikeThisFieldQuery
 */
public class FuzzyQueriesUtilities {
    private static final  String LIKE_TEXT = "like_text";
    private static final  String IGNORE_TF = "ignore_tf";
    private static final  String MAX_QUERY_TERMS = "max_query_terms";
    private static final  String PREFIX_LENGTH = "prefix_length";

    private String like_text;
    private Boolean ignore_tf;
    private Integer max_query_terms;
    private Integer prefix_length;

    public void setLike_text(String like_text) {
        this.like_text = like_text;
    }

    public void setIgnore_tf(Boolean ignore_tf) {
        this.ignore_tf = ignore_tf;
    }

    public void setMax_query_terms(Integer max_query_terms) {
        this.max_query_terms = max_query_terms;
    }

    public void setPrefix_length(Integer prefix_length) {
        this.prefix_length = prefix_length;
    }

    public void applyMatchUtilitiesOnJson(JsonObject queryJson) {
        if(like_text != null) {
            queryJson.add(LIKE_TEXT, new JsonPrimitive(like_text));
        }
        if(ignore_tf != null) {
            queryJson.add(IGNORE_TF, new JsonPrimitive(ignore_tf));
        }
        if(max_query_terms != null) {
            queryJson.add(MAX_QUERY_TERMS, new JsonPrimitive(max_query_terms));
        }
        if(prefix_length != null) {
            queryJson.add(PREFIX_LENGTH, new JsonPrimitive(prefix_length));
        }
    }
}
