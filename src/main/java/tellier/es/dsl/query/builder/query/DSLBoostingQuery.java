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
 * Elastic search boosting query
 *
 * See details here : http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-boosting-query.html
 */
public class DSLBoostingQuery implements DSLQuery {
    private static final  String POSITIVE = "positive";
    private static final  String NEGATIVE = "negative";
    private static final  String NEGATIVE_BOOST = "negative_boost";
    private static final  String BOOST = "boost";
    private static final  String BOOSTING = "boosting";

    private DSLQuery positiveQuery;
    private DSLQuery negativeQuery;
    private double negativeBoost;
    private Double boost;

    public DSLBoostingQuery(DSLQuery positiveQuery, DSLQuery negativeQuery, double negativeBoost) {
        this.positiveQuery = positiveQuery;
        this.negativeQuery = negativeQuery;
        this.negativeBoost = negativeBoost;
    }

    public DSLBoostingQuery setBoost(Double boost) {
        this.boost = boost;
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject boostingBody = new JsonObject();
        boostingBody.add(POSITIVE, positiveQuery.getQueryAsJson());
        boostingBody.add(NEGATIVE, negativeQuery.getQueryAsJson());
        boostingBody.add(NEGATIVE_BOOST, new JsonPrimitive(negativeBoost));
        if(boost != null) {
            boostingBody.add(BOOST, new JsonPrimitive(boost));
        }
        JsonObject result = new JsonObject();
        result.add(BOOSTING, boostingBody);
        return result;
    }

}
