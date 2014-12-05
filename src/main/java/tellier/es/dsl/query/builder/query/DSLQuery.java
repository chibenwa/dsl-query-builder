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

/**
 * A DSLQuery is an object that represent a DSL query.
 *
 * The basic operation is to get it as a JSON.
 */
public interface DSLQuery {
    public final String MATCH_ALL = "match_all";
    public final String MUST = "must";
    public final String SHOULD = "should";
    public final String MUST_NOT = "must_not";
    public final String NESTED = "nested";
    public final String SCORE_MODE = "score_mode";
    public final String PATH = "path";
    public final String BOOL = "bool";
    public final String AVG = "avg";
    public final String SUM = "sum";
    public final String MAX = "max";
    public final String NONE = "none";
    public final String QUERY = "query";
    public final String TYPE = "type";

    /**
     *
     * @return JsonObject that will perform this query
     */
    public JsonObject getQueryAsJson();
}
