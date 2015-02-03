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
import tellier.es.dsl.query.builder.Utilities.DSLPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Geo Shape Query
 *
 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-geo-shape-query.html
 */
public class DSLGeoShapeQuery implements DSLQuery {

    private final static String GEO_SHAPE = "geo_shape";
    private final static String LOCATION = "location";
    private final static String SHAPE = "shape";
    private final static String TYPE = "type";
    private final static String COORDINATES = "coordinates";

    private String type;
    private List<DSLPoint> points = new ArrayList<DSLPoint>();

    public DSLGeoShapeQuery(String type) {
        this.type = type;
    }

    public DSLGeoShapeQuery addPoint(DSLPoint point) {
        points.add(point);
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject result = new JsonObject();
        JsonObject geoShapeObject = new JsonObject();
        JsonObject locationObject = new JsonObject();
        JsonObject shapeObject = new JsonObject();
        result.add(GEO_SHAPE, geoShapeObject);
        geoShapeObject.add(LOCATION, locationObject);
        locationObject.add(SHAPE, shapeObject);
        shapeObject.add(TYPE, new JsonPrimitive(type));
        shapeObject.add(COORDINATES, getPointsArray());
        return result;
    }

    private JsonArray getPointsArray() {
        JsonArray pointsArray = new JsonArray();
        for(DSLPoint point : points) {
            pointsArray.add(point.getJsonArray());
        }
        return pointsArray;
    }

}
