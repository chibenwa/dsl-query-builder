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
 * Represent Ids Query
 *
 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-ids-query.html
 */
public class DSLIdsQuery implements DSLQuery {

    private static final String IDS = "ids";
    private static final String TYPE = "type";
    private static final String VALUES = "values";

    private List<String> values = new ArrayList<String>();
    private List<String> types = new ArrayList<String>();

    public DSLIdsQuery addValue(String value) {
        values.add(value);
        return this;
    }

    public DSLIdsQuery addType(String type) {
        types.add(type);
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject result = new JsonObject();
        JsonObject idsObject = new JsonObject();
        result.add(IDS, idsObject);
        if(types.size()>0) {
            if(types.size() == 1) {
                idsObject.add(TYPE, new JsonPrimitive(types.get(0)));
            } else {
                idsObject.add(TYPE, getTypesArray());
            }
        }
        idsObject.add(VALUES, getIdsArray());
        return result;
    }

    private JsonArray getIdsArray() {
        JsonArray idsArray = new JsonArray();
        for(String id : values) {
            idsArray.add(new JsonPrimitive(id));
        }
        return idsArray;
    }

    private JsonArray getTypesArray() {
        JsonArray typesArray = new JsonArray();
        for(String type : types) {
            typesArray.add(new JsonPrimitive(type));
        }
        return typesArray;
    }

}
