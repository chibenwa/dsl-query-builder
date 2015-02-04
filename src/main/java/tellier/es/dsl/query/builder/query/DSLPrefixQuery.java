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

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Represents a Prefix query
 *
 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-prefix-query.html
 */
public class DSLPrefixQuery implements DSLQuery {

    private static final String PREFIX = "prefix";
    private static final String BOOST = "boost";

    private String field;
    private Double boost;
    private String prefix;

    public DSLPrefixQuery(String field, String prefix) {
        this.field = field;
        this.prefix = prefix;
    }

    public DSLPrefixQuery setBoost(Double boost) {
        this.boost = boost;
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject result = new JsonObject();
        JsonObject prefixObject = new JsonObject();
        result.add(PREFIX, prefixObject);
        if( boost == null) {
            prefixObject.add(field, new JsonPrimitive(prefix));
        } else {
            JsonObject innerPreficObject = new JsonObject();
            innerPreficObject.add(PREFIX, new JsonPrimitive(prefix));
            innerPreficObject.add(BOOST, new JsonPrimitive(boost));
            prefixObject.add(field, innerPreficObject);
        }
        return result;
    }

}
