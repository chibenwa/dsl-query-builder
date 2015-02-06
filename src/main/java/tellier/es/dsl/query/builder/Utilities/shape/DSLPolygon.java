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
package tellier.es.dsl.query.builder.Utilities.shape;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.List;

/**
 * Represents a polygon shape
 */
public class DSLPolygon implements DSLShape {

    private final static String POLYGON = "polygon";

    private List<DSLPoint> points;

    public DSLPolygon addPoint(DSLPoint point) {
        points.add(point);
        return this;
    }

    JsonArray getPointArray() {
        JsonArray result = new JsonArray();
        for(DSLPoint point : points) {
            result.add(point.getJsonArray());
        }
        return result;
    }

    public JsonElement getShapeAsJson() {
        JsonObject result = new JsonObject();
        result.add(TYPE, new JsonPrimitive(POLYGON));
        result.add(COORDINATES, getPointArray());
        return result;
    }
}
