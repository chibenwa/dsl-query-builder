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

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import tellier.es.dsl.query.builder.Utilities.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents Script filter
 * 
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-script-filter.html
 */
public class DSLScriptFilter implements DSLFilter {
    
    private static final String SCRIPT = "script";
    private static final String PARAMS = "params";

    private String script;
    private List<Param> params = new ArrayList<Param>();
    private Boolean cache;
    


    public DSLScriptFilter(String script) {
        this.script = script;
    }

    public DSLScriptFilter setCache(Boolean cache) {
        this.cache = cache;
        return this;
    }
    
    public DSLScriptFilter addParam(Param param) {
        params.add(param);
        return this;
    }
    
    public JsonObject getFilterAsJson() {
        JsonObject result = new JsonObject();
        JsonObject scriptObject = new JsonObject();
        JsonObject paramsObject = new JsonObject();
        result.add(SCRIPT, scriptObject);
        scriptObject.add(SCRIPT, new JsonPrimitive(script));
        if(params.size() > 0) {
            scriptObject.add(PARAMS, paramsObject);
            for (Param param : params) {
                paramsObject.add(param.getName(), param.getJsonPrimitive());
            }
        }
        if(cache != null) {
            scriptObject.add(CACHE, new JsonPrimitive(cache));
        }
        return result;
    }
}
