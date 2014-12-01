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

import java.util.ArrayList;
import java.util.List;

/**
 * Code factorisation for Boolean queries.
 *
 * Note : this bool builder is not as generic as the elastic search API,
 * because we have a simple use case : our boolean queries will always have
 * only one type of items : should, should not or must not. You can use a DSLAndQuery
 * to combine these elements in a single query.
 */
public class DSLBoolQuery implements DSLQuery {
    private List<DSLQuery> mustQueries;
    private List<DSLQuery> shouldQueries;
    private List<DSLQuery> must_notQueries;

    public DSLBoolQuery() {
        this.mustQueries = new ArrayList<DSLQuery>();
        this.shouldQueries = new ArrayList<DSLQuery>();
        this.must_notQueries = new ArrayList<DSLQuery>();
    }

    /**
     * @return The sub query count
     */
    public int getQueryCount() {
        return mustQueries.size() + shouldQueries.size() + must_notQueries.size();
    }

    /**
     * @param query A query you want to add to the sub queries
     */
    /*public void addQuery(DSLQuery query) {
        queries.add(query);
    }*/

    public void must(DSLQuery query) {
        mustQueries.add(query);
    }

    public void should(DSLQuery query) {
        shouldQueries.add(query);
    }

    public void must_not(DSLQuery query) {
        must_notQueries.add(query);
    }

    protected JsonArray getQueriesJsonArray(List<DSLQuery> queries) {
        if(queries.size() == 0) {
            return null;
        }
        JsonArray result = new JsonArray();
        for(DSLQuery query : queries) {
            result.add(query.getQueryAsJson());
        }
        return result;
    }

    public JsonObject getQueryAsJson() {
        JsonObject boolBody = new JsonObject();
        if( mustQueries.size() > 0) {
            JsonArray mustArray = getQueriesJsonArray(mustQueries);
            boolBody.add(MUST, mustArray);
        }
        if(shouldQueries.size() > 0) {
            JsonArray shouldArray = getQueriesJsonArray(shouldQueries);
            boolBody.add(SHOULD, shouldArray);
        }
        if(must_notQueries.size() > 0) {
            JsonArray must_notArray = getQueriesJsonArray(must_notQueries);
            boolBody.add(MUST_NOT, must_notArray);
        }
        JsonObject boolQuery = new JsonObject();
        boolQuery.add(BOOL, boolBody);
        return boolQuery;
    }

}
