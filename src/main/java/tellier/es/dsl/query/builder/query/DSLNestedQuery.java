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
 * Represents a nested query
 * 
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-nested-query.html
 */
public class DSLNestedQuery implements DSLQuery {
    
    private static final String NESTED = "nested";
    private static final String SCORE_MODE = "score_mode";
    private static final String PATH = "path";

    private String path;
    private Score_mode scoreMode;
    private DSLQuery subQuery;

    public enum Score_mode {
        AVG("avg"),
        SUM("sum"),
        MAX("max"),
        NONE("none");
        
        private String tag;
        
        Score_mode(String tag) {
            this.tag = tag;
        }
        
        public String getTag() {
            return tag;
        }
    }

    public JsonObject getQueryAsJson() {
        JsonObject nestedContent = new JsonObject();
        nestedContent.add(PATH, new JsonPrimitive(path));
        if(scoreMode != Score_mode.AVG) {
            nestedContent.add(SCORE_MODE, new JsonPrimitive(scoreMode.getTag()));
        }
        nestedContent.add(QUERY, subQuery.getQueryAsJson());
        JsonObject finalQuery = new JsonObject();
        finalQuery.add(NESTED, nestedContent);
        return finalQuery;
    }

    public DSLNestedQuery(String path, Score_mode scoreMode, DSLQuery subQuery) {
        this.path = path;
        this.scoreMode = scoreMode;
        this.subQuery = subQuery;
    }

    public DSLNestedQuery setPath(String path) {
        this.path = path;
        return this;
    }

    public DSLNestedQuery setScoreMode(Score_mode scoreMode) {
        this.scoreMode = scoreMode;
        return this;
    }

    public DSLNestedQuery setSubQuery(DSLQuery subQuery) {
        this.subQuery = subQuery;
        return this;
    }
}
