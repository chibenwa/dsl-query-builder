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
 * Represent a Has Parent Query
 *
 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-has-parent-query.html
 */
public class DSLHasParentQuery implements DSLQuery {

    private static final String HAS_PARENT = "has_parent";
    private static final String PARENT_TYPE = "parent_type";
    private static final String QUERY = "query";
    private static final String SCORE_MODE = "score_mode";

    private String parentType;
    private DSLQuery subQuery;
    private ScoreMode scoreMode;

    public enum ScoreMode {
        SCORE("score"),
        NONE("none");

        private String tag;

        ScoreMode(String tag) {
            this.tag = tag;
        }

        private String getTag() {
            return tag;
        }
    }

    DSLHasParentQuery(String parentType, DSLQuery subQuery) {
        this.subQuery = subQuery;
        this.parentType = parentType;
        this.scoreMode = ScoreMode.NONE;
    }

    public DSLHasParentQuery setScoreMode(ScoreMode scoreMode) {
        this.scoreMode = scoreMode;
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject result = new JsonObject();
        JsonObject hasParentQuery = new JsonObject();
        result.add(HAS_PARENT, hasParentQuery);
        hasParentQuery.add(PARENT_TYPE, new JsonPrimitive(parentType));
        hasParentQuery.add(QUERY, subQuery.getQueryAsJson());
        if(scoreMode != ScoreMode.NONE) {
            hasParentQuery.add(SCORE_MODE, new JsonPrimitive(scoreMode.getTag()));
        }
        return result;
    }

}
