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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import tellier.es.dsl.query.builder.Utilities.MatchUtilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an ElasticSearch Dis Max Query.
 *
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-dis-max-query.html
 */
public class DSLDisMaxQuery implements DSLQuery {

    private static final  String DIS_MAX = "dis_max";
    private static final  String QUERIES = "queries";

    public MatchUtilities matchUtilities;
    public List<DSLQuery> queryList;

    public DSLDisMaxQuery() {
        matchUtilities = new MatchUtilities();
        queryList = new ArrayList<DSLQuery>();
    }

    public DSLDisMaxQuery setBoost(Double boost) {
        matchUtilities.setBoost(boost);
        return this;
    }

    public DSLDisMaxQuery setTieBreaker(Double tieBreaker) {
        matchUtilities.setTieBraker(tieBreaker);
        return this;
    }

    public DSLDisMaxQuery addQuery(DSLQuery query) {
        queryList.add(query);
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonArray queryArray = new JsonArray();
        for(DSLQuery query : queryList) {
            queryArray.add(query.getQueryAsJson());
        }
        JsonObject disMaxJson = new JsonObject();
        matchUtilities.applyMatchUtilitiesOnJson(disMaxJson);
        disMaxJson.add(QUERIES, queryArray);
        JsonObject result = new JsonObject();
        result.add(DIS_MAX, disMaxJson);
        return result;
    }

}
