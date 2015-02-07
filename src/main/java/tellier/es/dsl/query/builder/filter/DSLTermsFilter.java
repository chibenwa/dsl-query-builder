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
 * Terms filter
 * 
 *  See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-terms-filter.html
 */
public class DSLTermsFilter implements DSLFilter {
    
    private final static String TERMS = "terms";
    private final static String EXECUTION = "execution";
    private final static String CACHE = "cache";
    private final static String INDEX = "index";
    private final static String TYPE = "type";
    private final static String ID = "id";
    private final static String PATH = "path";
    private final static String ROUTING = "routing";

    public enum ExecutionType {
        Plain("plain"),
        Fielddata("fielddata"),
        Bool("bool"),
        And("and"),
        Or("or"),
        None("none");

        private String tag;

        ExecutionType(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }
    }
    
    private List<String> terms = new ArrayList<String>();
    private String field;
    private ExecutionType execution = ExecutionType.None;
    private String index;
    private String type;
    private String id;
    private String path;
    private String routing;
    private Boolean cache;

    public DSLTermsFilter(String field) {
        this.field = field;
    }

    public DSLTermsFilter addTerm(String term) {
        terms.add(term);
        return this;
    }

    public DSLTermsFilter setExecution(ExecutionType execution) {
        this.execution = execution;
        return this;
    }

    public DSLTermsFilter setIndex(String index) {
        this.index = index;
        return this;
    }

    public DSLTermsFilter setType(String type) {
        this.type = type;
        return this;
    }

    public DSLTermsFilter setId(String id) {
        this.id = id;
        return this;
    }

    public DSLTermsFilter setPath(String path) {
        this.path = path;
        return this;
    }

    public DSLTermsFilter setRouting(String routing) {
        this.routing = routing;
        return this;
    }

    public DSLTermsFilter setCache(Boolean cache) {
        this.cache = cache;
        return this;
    }
    
    private JsonArray getTermsArray() {
        JsonArray termsArray = new JsonArray();
        for(String term : terms) {
            termsArray.add(new JsonPrimitive(term));
        }
        return termsArray;
    }
    
    public JsonObject getFilterAsJson() {
        JsonObject result = new JsonObject();
        JsonObject termsObject = new JsonObject();
        result.add(TERMS, termsObject);
        termsObject.add(field, getTermsArray());
        if(execution != ExecutionType.None) {
            termsObject.add(EXECUTION, new JsonPrimitive(execution.getTag()));
        }
        if(index != null) {
            termsObject.add(INDEX, new JsonPrimitive(index));
        }
        if(type != null) {
            termsObject.add(TYPE, new JsonPrimitive(type));
        }
        if(id != null) {
            termsObject.add(ID, new JsonPrimitive(id));
        }
        if(path != null) {
            termsObject.add(PATH, new JsonPrimitive(path));
        }
        if(routing != null) {
            termsObject.add(ROUTING, new JsonPrimitive(routing));
        }
        if(cache != null) {
            termsObject.add(CACHE, new JsonPrimitive(cache));
        }
        return result;
    }

}
