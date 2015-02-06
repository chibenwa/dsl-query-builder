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
import tellier.es.dsl.query.builder.filter.DSLFilter;

/**
 * Elastic search constant score query
 *
 * See details here : http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-constant-score-query.html
 */
public class DSLConstantScoreQuery implements DSLQuery {
    private DSLFilter filter;
    private DSLQuery query;
    private double boost;

    private static final String BOOST = "boost";
    private static final String CONSTANT_SCORE = "constant_score";
    private static final String FILTER = "filter";

    public DSLConstantScoreQuery(DSLQuery query, double boost) {
        this.query = query;
        this.boost = boost;
    }

    public DSLConstantScoreQuery(DSLFilter filter, double boost) {
        this.filter = filter;
        this.boost = boost;
    }

    public JsonObject getQueryAsJson() {
        JsonObject constantScoreBody = new JsonObject();
        if(filter != null) {
            constantScoreBody.add(FILTER, filter.getFilterAsJson());
        } else {
            constantScoreBody.add(QUERY, query.getQueryAsJson());
        }
        constantScoreBody.add(BOOST,  new JsonPrimitive(boost));
        JsonObject result = new JsonObject();
        result.add(CONSTANT_SCORE, constantScoreBody );
        return result;
    }
}
