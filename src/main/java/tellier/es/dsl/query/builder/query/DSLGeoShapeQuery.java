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
import tellier.es.dsl.query.builder.Utilities.shape.DSLShape;
import tellier.es.dsl.query.builder.filter.DSLGeoShapeFilter;

/**
 * Represents a Geo Shape Query
 *
 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-geo-shape-query.html
 */
public class DSLGeoShapeQuery implements DSLQuery {

    private DSLGeoShapeFilter filter;
    
    public DSLGeoShapeQuery(String field, String id, String index, String type, String path) {
        filter = new DSLGeoShapeFilter(field, id, index, type, path);
    }

    public DSLGeoShapeQuery(String field, DSLShape shape) {
        filter = new DSLGeoShapeFilter(field, shape);
    }
    
    public JsonObject getQueryAsJson() {
        return filter.getFilterAsJson();
    }

}
