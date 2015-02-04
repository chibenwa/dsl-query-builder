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
package tellier.es.dsl.query.builder.Utilities;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Represent a document ( used for more like this query
 */
public class DSLDoc {

    private static final String INDEX = "_index";
    private static final String TYPE = "_type";
    private final static String ID = "_id";

    private String index;
    private String type;
    private String id;

    public DSLDoc(String index, String type, String id) {
        this.index = index;
        this.type = type;
        this.id = id;
    }

    public JsonObject getAsJson() {
        JsonObject documentAsJson = new JsonObject();
        documentAsJson.add(INDEX, new JsonPrimitive(index));
        documentAsJson.add(TYPE, new JsonPrimitive(type));
        documentAsJson.add(ID, new JsonPrimitive(id));
        return documentAsJson;
    }

}
