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
 * Represents a Has Child Query
 *
 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-has-child-query.html
 */
public class DSLHasChildQuery implements DSLQuery {

    private static final String HAS_CHILD = "has_child";
    private static final String QUERY = "query";
    private static final String TYPE = "type";
    private static final String SCORE_MODE = "score_mode";

    public enum ScoreMode {
        MAX("max"),
        SUM("sum"),
        AVG("avg"),
        NONE("none");

        private String tag;

        ScoreMode(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }
    }

    private DSLQuery subQuery;
    private ScoreMode scoreMode;
    private String type;

    public DSLHasChildQuery(String type, DSLQuery query) {
        this.type = type;
        this.subQuery = query;
        this.scoreMode = ScoreMode.NONE;
    }

    public DSLHasChildQuery setScoreMode(ScoreMode scoreMode) {
        this.scoreMode = scoreMode;
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject result = new JsonObject();
        JsonObject hasChildObject = new JsonObject();
        result.add(HAS_CHILD, hasChildObject);
        hasChildObject.add(TYPE, new JsonPrimitive(type));
        if(scoreMode != ScoreMode.NONE) {
            hasChildObject.add(SCORE_MODE, new JsonPrimitive(scoreMode.getTag()));
        }
        hasChildObject.add(QUERY, subQuery.getQueryAsJson());
        return result;
    }

}
