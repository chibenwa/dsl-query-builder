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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import tellier.es.dsl.query.builder.Utilities.DSLGeoPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Geo polygon filter
 * 
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-geo-polygon-filter.html
 */
public class DSLGeoPolygonFilter implements DSLFilter {
    
    private static final String GEO_POLYGON = "geo_polygon";
    private static final String POINTS = "points";
    
    private List<DSLGeoPoint> points = new ArrayList<DSLGeoPoint>();
    private String field;

    public DSLGeoPolygonFilter(String field) {
        this.field = field;
    }

    public DSLGeoPolygonFilter addPoint(DSLGeoPoint point) {
        points.add(point);
        return this;
    }
    
    public JsonObject getQueryAsJson() {
        JsonObject result = new JsonObject();
        JsonObject geoPolygonObject = new JsonObject();
        JsonObject fieldObject = new JsonObject();
        result.add(GEO_POLYGON, geoPolygonObject);
        geoPolygonObject.add(field, fieldObject);
        JsonArray pointsArray = new JsonArray();
        for(DSLGeoPoint point : points) {
            pointsArray.add(point.getPointAsJsonElement());
        }
        fieldObject.add(POINTS, pointsArray);
        return result;
    }
}
