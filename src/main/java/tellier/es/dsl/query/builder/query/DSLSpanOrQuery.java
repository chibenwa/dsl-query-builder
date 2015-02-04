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

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Span Or query
 */
public class DSLSpanOrQuery implements DSLSpanQuery {

    private static final String SPAN_OR = "span_or";
    private static final String CLAUSES = "clauses";

    private List<DSLSpanQuery> clauses = new ArrayList<DSLSpanQuery>();

    public DSLSpanOrQuery addClause(DSLSpanQuery clause) {
        clauses.add(clause);
        return this;
    }

    private JsonArray getClausesArray() {
        JsonArray clausesArray = new JsonArray();
        for(DSLSpanQuery clause : clauses) {
            clausesArray.add(clause.getQueryAsJson());
        }
        return clausesArray;
    }

    public JsonObject getQueryAsJson() {
        JsonObject result = new JsonObject();
        JsonObject spanOrObject = new JsonObject();
        result.add(SPAN_OR, spanOrObject);
        spanOrObject.add(CLAUSES, getClausesArray());
        return result;
    }
}
