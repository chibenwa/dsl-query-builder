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

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a point, for instance for a GeoShape Query
 */
public class DSLPoint implements DSLShape {

    private final static String POINT = "point";
    
    private List<Double> coordinates = new ArrayList<Double>();

    public DSLPoint(Double x) {
        coordinates.add(x);
    }

    public DSLPoint(Double x, Double y) {
        this(x);
        coordinates.add(y);
    }

    public DSLPoint(Double x, Double y, Double z) {
        this(x, y);
        coordinates.add(z);
    }

    public DSLPoint(Double x, Double y, Double z, Double a) {
        this(x, y, z);
        coordinates.add(a);
    }

    public DSLPoint(Double x, Double y, Double z, Double a, Double b) {
        this(x, y, z, a);
        coordinates.add(b);
    }

    public JsonArray getJsonArray() {
        return getCoordinatesArray();
    }

    private JsonArray getCoordinatesArray() {
        JsonArray coordinatesArray = new JsonArray();
        for(Double coordinate : coordinates) {
            coordinatesArray.add(new JsonPrimitive(coordinate));
        }
        return coordinatesArray;
    }

    public JsonElement getShapeAsJson() {
        JsonObject result = new JsonObject();
        result.add(TYPE, new JsonPrimitive(POINT));
        result.add(COORDINATES, getCoordinatesArray());
        return result;
    }
}
