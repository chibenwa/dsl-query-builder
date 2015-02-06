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
import tellier.es.dsl.query.builder.Utilities.DSLDistance;
import tellier.es.dsl.query.builder.Utilities.DSLGeoPoint;

/**
 * Represents a Geo distance range filter
 * 
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-geo-distance-range-filter.html
 */
public class DSLGeoDistanceRangeFilter implements DSLFilter {
    
    private final static String GEO_DISTANCE_RANGE = "geo_distance_range";
    private final static String FROM = "from";
    private final static String TO = "to";
    
    private String field;
    private DSLDistance from;
    private DSLDistance to;
    private DSLGeoPoint point;

    public DSLGeoDistanceRangeFilter(String field, DSLDistance from, DSLDistance to, DSLGeoPoint point) {
        this.field = field;
        this.from = from;
        this.to = to;
        this.point = point;
    }
    
    public JsonObject getQueryAsJson() {
        JsonObject result = new JsonObject();
        JsonObject geoDistanceRangeObject = new JsonObject();
        result.add(GEO_DISTANCE_RANGE, geoDistanceRangeObject);
        geoDistanceRangeObject.add(FROM, new JsonPrimitive(from.toString()));
        geoDistanceRangeObject.add(TO, new JsonPrimitive(to.toString()));
        geoDistanceRangeObject.add(field, point.getPointAsJson());
        return result;
    }
}
