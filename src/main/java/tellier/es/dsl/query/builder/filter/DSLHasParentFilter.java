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
import tellier.es.dsl.query.builder.query.DSLQuery;

/**
 * Represents a has parent filter.
 * 
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-has-parent-filter.html
 */
public class DSLHasParentFilter implements DSLFilter {
    
    private static final String HAS_PARENT = "has_parent";
    private static final String PARENT_TYPE = "parent_type";
    private static final String QUERY = "query";
    private static final String MIN_CHILDREN = "min_children";
    private static final String MAX_CHILDREN = "max_children";
    
    private String parentType;
    private DSLQuery subQuery;
    private Integer maxChildren;
    private Integer minChildren;

    public DSLHasParentFilter(String parentType, DSLQuery subQuery) {
        this.parentType = parentType;
        this.subQuery = subQuery;
    }

    public DSLHasParentFilter setMinChildren(Integer minChildren) {
        this.minChildren = minChildren;
        return this;
    }

    public DSLHasParentFilter setMaxChildren(Integer maxChildren) {
        this.maxChildren = maxChildren;
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject result = new JsonObject();
        JsonObject hasChildObject = new JsonObject();
        result.add(HAS_PARENT, hasChildObject);
        hasChildObject.add(PARENT_TYPE, new JsonPrimitive(parentType));
        hasChildObject.add(QUERY, subQuery.getQueryAsJson());
        if (maxChildren != null) {
            hasChildObject.add(MAX_CHILDREN, new JsonPrimitive(maxChildren));
        }
        if(minChildren != null) {
            hasChildObject.add(MIN_CHILDREN, new JsonPrimitive(minChildren));
        }
        return result;
    }
    
}
