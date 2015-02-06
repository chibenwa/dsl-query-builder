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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Represents a geo localized point for Geo filters
 */
public class DSLGeoPoint {
    
    private static final String LAT = "lat";
    private static final String LON = "lon";
    
    public enum Mode {
        LatLon,
        Array,
        Properties
    }
    
    private String geoHash;
    private double longitude;
    private double latitude;
    private Mode mode;
    private boolean isGeoHash = false;
    
    public DSLGeoPoint(String geoHash) {
        this.isGeoHash = true;
        this.geoHash = geoHash;
    }
    
    public DSLGeoPoint(double latitude, double longitude) {
        this(Mode.Properties, latitude, longitude);
    }
    
    public DSLGeoPoint(Mode mode, double latitude, double longitude) {
        this.mode = mode;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    
    public JsonElement getPointAsJson() {
        if(isGeoHash) {
            return new JsonPrimitive(geoHash);
        } else if(mode == Mode.Properties) {
            JsonObject result = new JsonObject();
            result.add(LAT, new JsonPrimitive(latitude));
            result.add(LON, new JsonPrimitive(longitude));
            return result;
        } else if(mode == Mode.Array) {
            JsonArray result = new JsonArray();
            result.add(new JsonPrimitive(longitude));
            result.add(new JsonPrimitive(latitude));
            return result;
        } 
        // mode == Mode.LatLon
        return new JsonPrimitive(latitude +", "+longitude);
    }
    
}
