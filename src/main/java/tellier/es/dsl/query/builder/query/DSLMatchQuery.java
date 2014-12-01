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
 * Represents a query getting documents matching a search criterion in a given field.
 */
public class DSLMatchQuery implements DSLQuery{
    private String field;
    private String searchCriterion;

    /**
     * @param field The document field you want to search into
     * @param searchCriterion The pattern you want to search
     */
    public DSLMatchQuery(String field, String searchCriterion) {
        this.field = field;
        this.searchCriterion = searchCriterion;
    }

    public JsonObject getQueryAsJson() {
        if(field.isEmpty() || searchCriterion.isEmpty()) {
            return null;
        }
        JsonObject matchField = new JsonObject();
        JsonPrimitive searchPrimitive = new JsonPrimitive(searchCriterion);
        matchField.add(field, searchPrimitive);
        JsonObject result = new JsonObject();
        result.add(MATCH, matchField);
        return result;
    }
}