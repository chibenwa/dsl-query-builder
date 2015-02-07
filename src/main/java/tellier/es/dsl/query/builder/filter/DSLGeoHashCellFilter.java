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

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import tellier.es.dsl.query.builder.Utilities.DSLDistance;
import tellier.es.dsl.query.builder.Utilities.DSLGeoPoint;

/**
 * Geo hash cell filter
 * 
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-geohash-cell-filter.html
 */
public class DSLGeoHashCellFilter implements DSLFilter {
    
    private static final String GEOHASH_CELL = "geohash_cell";
    private static final String PRECISION = "precision";
    private static final String NEIGHBORS = "neighbors";
    
    private String field;
    private DSLGeoPoint point;
    private DSLDistance precisionDistance;
    private Number precision;
    private Boolean neighbors;
    private Boolean cache;

    public DSLGeoHashCellFilter(String field, DSLGeoPoint point) {
        this.field = field;
        this.point = point;
    }

    public DSLGeoHashCellFilter setCache(Boolean cache) {
        this.cache = cache;
        return this;
    }

    public DSLGeoHashCellFilter setNeighbors(Boolean neighbors) {
        this.neighbors = neighbors;
        return this;
    }

    public DSLGeoHashCellFilter setPrecision(DSLDistance distance) {
        this.precisionDistance = distance;
        return this;
    }

    public DSLGeoHashCellFilter setPrecision(Number number) {
        this.precision = number;
        return this;
    }
    
    public JsonObject getFilterAsJson() {
        JsonObject result = new JsonObject();
        JsonObject geohashObject = new JsonObject();
        result.add(GEOHASH_CELL, geohashObject);
        geohashObject.add(field, point.getPointAsJson());
        if(precision != null) {
            geohashObject.add(PRECISION, new JsonPrimitive(precision));
        } else if(precisionDistance != null) {
            geohashObject.add(PRECISION, new JsonPrimitive(precisionDistance.toString()));
        }
        if(neighbors != null) {
            geohashObject.add(NEIGHBORS, new JsonPrimitive(neighbors));
        }
        if(cache != null) {
            geohashObject.add(CACHE, new JsonPrimitive(cache));
        }
        return result;
    }
}
