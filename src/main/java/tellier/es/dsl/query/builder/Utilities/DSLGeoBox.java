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
package tellier.es.dsl.query.builder.Utilities;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Represents a geo box
 */
public class DSLGeoBox {
    
    private static final String TOP_LEFT = "top_left";
    private static final String BOTTOM_RIGHT = "bottom_right";
    private static final String TOP = "top";
    private static final String RIGHT = "right";
    private static final String LEFT = "left";
    private static final String BOTTOM = "bottom";

    private Mode mode;
    
    private DSLGeoPoint topLeft;
    private DSLGeoPoint bottomRight;
    
    private double top;
    private double left;
    private double bottom;
    private double right;
    
    enum Mode {
        Points,
        Vertices
    }

    public DSLGeoBox(double top, double left, double bottom, double right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
        this.mode = Mode.Vertices;
    }

    public DSLGeoBox(DSLGeoPoint topLeft, DSLGeoPoint bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.mode = Mode.Points;
    }
    
    public JsonObject getBoxAsJsonObject() {
        JsonObject result = new JsonObject();
        if(mode == Mode.Points) {
            result.add(TOP_LEFT, topLeft.getPointAsJson());
            result.add(BOTTOM_RIGHT, bottomRight.getPointAsJson());
        } else {
            result.add(TOP, new JsonPrimitive(top));
            result.add(BOTTOM, new JsonPrimitive(bottom));
            result.add(RIGHT, new JsonPrimitive(right));
            result.add(LEFT, new JsonPrimitive(left));
        }
        return result;
    }
}
