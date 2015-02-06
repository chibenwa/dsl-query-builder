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
package tellier.es.dsl.query.builder.filter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Indices filter
 * 
 * See  http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/filter-dsl-indices-filter.html
 */
public class DSLIndicesFilter implements DSLFilter {

    private final static String INDICES = "indices";
    private final static String FILTER= "filter";
    private final static String NO_MATCH_FILTER = "no_match_filter";

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
    private DSLFilter filter;
    private DSLFilter noMatchFilter;
    private NoMatchMode noMatchMode;

    public DSLIndicesFilter(DSLFilter filter) {
        this.filter = filter;
        this.noMatchMode = NoMatchMode.ALL;
    }

    public DSLIndicesFilter setNoMatchFilter(DSLFilter noMatchFilter) {
        this.noMatchFilter = noMatchFilter;
        return this;
    }

    public DSLIndicesFilter setNoMatchMode(NoMatchMode noMatchMode) {
        this.noMatchMode = noMatchMode;
        return this;
    }

    public DSLIndicesFilter addIndice(String indice) {
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
        indicesObject.add(FILTER, filter.getQueryAsJson());
        if(noMatchFilter != null) {
            indicesObject.add(NO_MATCH_FILTER, noMatchFilter.getQueryAsJson());
        } else if(noMatchMode != NoMatchMode.ALL) {
            indicesObject.add(NO_MATCH_FILTER, new JsonPrimitive(noMatchMode.getTag()));
        }
        return result;
    }
    
}
