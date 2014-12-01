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
 * Represents a term filter
 */
public class DSLTermFilter implements DSLFilter {
    private boolean cache;
    private String field;
    private String filterPattern;
    private boolean booleanValue;
    private Number number;

    enum Type {
        BOOLEAN,
        NUMBER,
        STRING
    }

    private Type type;

    public Type getType() {
        return type;
    }

    public DSLTermFilter(String field, boolean cache) {
        this.cache = cache;
        this.field = field;
    }

    public DSLTermFilter(String field) {
        this(field, true);
    }

    public DSLTermFilter boolFilter(boolean booleanValue) {
        this.booleanValue = booleanValue;
        this.type = Type.BOOLEAN;
        return this;
    }

    public DSLTermFilter numberFilter(Number number) {
        this.number = number;
        this.type = Type.NUMBER;
        return this;
    }

    public DSLTermFilter stringFilter(String filterPattern) {
        this.filterPattern = filterPattern;
        this.type = Type.STRING;
        return this;
    }

    public JsonObject getQueryAsJson() {
        if(field.isEmpty() ) {
            return null;
        }
        JsonObject termField = new JsonObject();
        JsonPrimitive searchPrimitive = getPrimitive();
        if(searchPrimitive == null) {
            return null;
        }
        termField.add(field, searchPrimitive);
        JsonObject result = new JsonObject();
        result.add(TERM, termField);
        if(!cache) {
            result.add(CACHE, new JsonPrimitive(false));
        }
        return result;
    }

    private JsonPrimitive getPrimitive() {
        switch (type) {
            case BOOLEAN:
                return new JsonPrimitive(booleanValue);
            case STRING:
                return new JsonPrimitive(filterPattern);
            case NUMBER:
                return new JsonPrimitive(number);
            default:
                return null;
        }
    }
}
