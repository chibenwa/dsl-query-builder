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

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class to factorize code used by DSLAndFilter, DSLOrFilter and DSLNotFilter
 */
public abstract class DSLBooleanOperationFilter implements DSLFilter {
    private List<DSLFilter> filters;

    public DSLBooleanOperationFilter() {
        this.filters = new ArrayList<DSLFilter>();
    }

    /**
     * @return The sub filter count
     */
    public int getFilterCount() {
        return filters.size();
    }

    /**
     * @param query A filter you want to add to the sub filters
     */
    public DSLBooleanOperationFilter addFilter(DSLFilter query) {
        filters.add(query);
        return this;
    }

    public abstract JsonObject getFilterAsJson();

    protected JsonArray getFilterAsJsonArray() {
        if(filters.size() == 0) {
            return null;
        }
        JsonArray result = new JsonArray();
        for(DSLFilter filter : filters) {
            result.add(filter.getFilterAsJson());
        }
        return result;
    }

    protected  JsonObject getBoolQueryAsJson(String boolOperation) {
        JsonArray jsonArray = getFilterAsJsonArray();
        JsonObject boolBody = new JsonObject();
        boolBody.add(boolOperation, jsonArray);
        return boolBody;
    }

}
