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

/**
 * Missing filter
 * 
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-missing-filter.html
 */
public class DSLMissingFilter implements DSLFilter {
    
    private static final String MISSING = "missing";
    private static final String FIELD = "field";
    private static final String EXISTENCE = "existence";
    private static final String NULL_VALUE = "null_value";
    
    private String field;
    private Boolean existence;
    private Boolean nullValue;

    public DSLMissingFilter(String field) {
        this.field = field;
    }

    public DSLMissingFilter setExistence(Boolean existence) {
        this.existence = existence;
        return this;
    }

    public DSLMissingFilter setNullValue(Boolean nullValue) {
        this.nullValue = nullValue;
        return this;
    }

    public JsonObject getFilterAsJson() {
        JsonObject result = new JsonObject();
        JsonObject missingObject = new JsonObject();
        result.add(MISSING, missingObject);
        missingObject.add(FIELD, new JsonPrimitive(field));
        if(existence != null) {
            missingObject.add(EXISTENCE, new JsonPrimitive(existence));
        }
        if(nullValue != null) {
            missingObject.add(NULL_VALUE, new JsonPrimitive(nullValue));
        }
        return result;
    }
}
