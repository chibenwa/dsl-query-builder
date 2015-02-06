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
 * Exists filter implementation
 * 
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-exists-filter.html
 */
public class DSLExistFilter implements DSLFilter {

    private final static String EXISTS = "exists";
    private final static String NULL_VALUE = "null_value";
    
    private String field;
    private String nullValue;

    public DSLExistFilter(String field) {
        this.field = field;
        nullValue = null;
    }

    public DSLExistFilter setNullValue(String nullValue) {
        this.nullValue = nullValue;
        return this;
    }

    public JsonObject getFilterAsJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add(FIELD, new JsonPrimitive(field));
        if(nullValue != null) {
            jsonObject.add(NULL_VALUE, new JsonPrimitive(nullValue));
        }
        JsonObject result = new JsonObject();
        result.add(EXISTS, jsonObject);
        return result;
    }


}
