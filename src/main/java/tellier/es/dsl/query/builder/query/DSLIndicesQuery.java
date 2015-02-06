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
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an indices query
 *
 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-indices-query.html
 */
public class DSLIndicesQuery implements DSLQuery {

    private final static String INDICES = "indices";
    private final static String QUERY = "query";
    private final static String NO_MATCH_QUERY = "no_match_query";
    
    public enum NoMatchMode {
        NONE("none"),
        ALL("all");
        
        private String tag;
        
        NoMatchMode(String tag) {
            this.tag = tag;
        }
        
        public String getTag() {
            return tag;
        }
    }

    private List<String> indices = new ArrayList<String>();
    private DSLQuery query;
    private DSLQuery noMatchQuery;
    private NoMatchMode noMatchMode;

    public DSLIndicesQuery(DSLQuery query) {
        this.query = query;
        this.noMatchMode = NoMatchMode.ALL;
    }

    public DSLIndicesQuery setNoMatchQuery(DSLQuery noMatchQuery) {
        this.noMatchQuery = noMatchQuery;
        return this;
    }

    public DSLIndicesQuery setNoMatchMode(NoMatchMode noMatchMode) {
        this.noMatchMode = noMatchMode;
        return this;
    }

    public DSLIndicesQuery addIndice(String indice) {
        indices.add(indice);
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject result = new JsonObject();
        JsonObject indicesObject = new JsonObject();
        result.add(INDICES, indicesObject);
        JsonArray indicesArray = new JsonArray();
        for(String indice : indices) {
            indicesArray.add(new JsonPrimitive(indice));
        }
        indicesObject.add(INDICES, indicesArray);
        indicesObject.add(QUERY, query.getQueryAsJson());
        if(noMatchQuery != null) {
            indicesObject.add(NO_MATCH_QUERY, noMatchQuery.getQueryAsJson());
        } else if(noMatchMode != NoMatchMode.ALL) {
            indicesObject.add(NO_MATCH_QUERY, new JsonPrimitive(noMatchMode.getTag()));
        }
        return result;
    }

}
