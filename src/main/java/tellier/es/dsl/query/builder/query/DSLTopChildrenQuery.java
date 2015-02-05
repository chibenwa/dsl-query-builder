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
 * Represents a Top Children Query
 *
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-top-children-query.html
 */
public class DSLTopChildrenQuery implements DSLQuery {

    private static final String TOP_CHILDREN = "top_children";
    private static final String TYPE = "type";
    private static final String QUERY = "query";
    private static final String SCORE = "score";
    private static final String FACTOR = "factor";
    private static final String INCREMENTAL_FACTOR = "incremental_factor";
    private static final String SCOPE = "_scope";

    public enum Score {
        MAX("max"),
        SUM("sum"),
        AVG("avg"),
        NONE("none");

        private String tag;

        Score(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }
    }

    private Score score;
    private String type;
    private DSLQuery subQuery;
    private Integer factor;
    private Integer incrementalFactor;
    private String scope;

    public DSLTopChildrenQuery(String type, DSLQuery subQuery) {
        this.type = type;
        this.subQuery = subQuery;
    }

    public DSLTopChildrenQuery setScore(Score score) {
        this.score = score;
        return this;
    }

    public DSLTopChildrenQuery setScope(String scope) {
        this.scope = scope;
        return this;
    }

    public DSLTopChildrenQuery setFactor(Integer factor) {
        this.factor = factor;
        return this;
    }

    public DSLTopChildrenQuery setIncrementalFactor(Integer incrementalFactor) {
        this.incrementalFactor = incrementalFactor;
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject result = new JsonObject();
        JsonObject topChildrenObject = new JsonObject();
        result.add(TOP_CHILDREN, topChildrenObject);
        topChildrenObject.add(TYPE, new JsonPrimitive(type));
        topChildrenObject.add(QUERY, subQuery.getQueryAsJson());
        if(scope != null) {
            topChildrenObject.add(SCOPE, new JsonPrimitive(scope));
        }
        if(score != Score.NONE) {
            topChildrenObject.add(SCORE, new JsonPrimitive(score.getTag()));
        }
        if(factor != null) {
            topChildrenObject.add(FACTOR, new JsonPrimitive(factor));
        }
        if(incrementalFactor != null) {
            topChildrenObject.add(INCREMENTAL_FACTOR, new JsonPrimitive(incrementalFactor));
        }
        return result;
    }
}
