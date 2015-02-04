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
 * Represents a Span Near Query
 */
public class DSLSpanNearQuery implements DSLSpanQuery {

    private static final String SPAN_NEAR = "span_near";
    private static final String CLAUSES = "clauses";
    private static final String SLOP = "slop";
    private static final String IN_ORDER = "in_order";
    private static final String COLLECT_PAYLOADS = "collect_payloads";

    private List<DSLSpanQuery> clauses = new ArrayList<DSLSpanQuery>();
    private Integer slop;
    private Boolean in_order;
    private Boolean collect_payloads;

    public DSLSpanNearQuery addClause(DSLSpanQuery clause) {
        clauses.add(clause);
        return this;
    }

    public DSLSpanNearQuery setSlop(Integer slop) {
        this.slop = slop;
        return this;
    }

    public DSLSpanNearQuery setInOrder(Boolean in_order) {
        this.in_order = in_order;
        return this;
    }

    public DSLSpanNearQuery setCollectPayloads(Boolean collect_payloads) {
        this.collect_payloads = collect_payloads;
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject result = new JsonObject();
        JsonObject spanNearObject = new JsonObject();
        result.add(SPAN_NEAR, spanNearObject);
        spanNearObject.add(CLAUSES, getClausesArray());
        if(slop != null) {
            spanNearObject.add(SLOP, new JsonPrimitive(slop));
        }
        if(in_order != null) {
            spanNearObject.add(IN_ORDER, new JsonPrimitive(in_order));
        }
        if(collect_payloads != null) {
            spanNearObject.add(COLLECT_PAYLOADS, new JsonPrimitive(collect_payloads));
        }
        return result;
    }

    private JsonArray getClausesArray() {
        JsonArray clausesArray = new JsonArray();
        for(DSLSpanQuery clause : clauses) {
            clausesArray.add(clause.getQueryAsJson());
        }
        return clausesArray;
    }

}
