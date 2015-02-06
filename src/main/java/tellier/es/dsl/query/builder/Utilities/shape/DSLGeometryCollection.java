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
 * Collection of shape
 */
public class DSLGeometryCollection implements DSLShape {
    
    private static final String GEOMETRY_COLLECTION = "geometrycollection";
    private static final String GEOMETRIES = "geometries";
    
    private List<DSLShape> shapes = new ArrayList<DSLShape>();
    
    public DSLGeometryCollection addShape(DSLShape shape) {
        shapes.add(shape);
        return this;
    }
    
    public JsonElement getShapeAsJson() {
        JsonObject result = new JsonObject();
        result.add(TYPE, new JsonPrimitive(GEOMETRY_COLLECTION));
        result.add(GEOMETRIES, getGeometriesArray());
        return result;
    }
    
    private JsonArray getGeometriesArray() {
        JsonArray result = new JsonArray();
        for(DSLShape shape : shapes) {
            result.add(shape.getShapeAsJson());
        }
        return result;
    }
}
