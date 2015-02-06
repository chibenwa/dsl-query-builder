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
import tellier.es.dsl.query.builder.Utilities.DSLGeoBox;

/**
 * Represents a Geo bounding box filter
 * 
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-geo-bounding-box-filter.html
 */
public class DSLGeoBoundingBoxFilter implements DSLFilter {
    
    private static final String GEO_BOUNDING_BOX = "geo_bounding_box";
    private static final String TYPE = "type";
    
    enum Type {
        Memory("memory"),
        Indexed("indexed"),
        Geo_Point("geo_point");
        
        private String tag;
        
        Type(String tag) {
            this.tag = tag;
        }
        
        public String getTag() {
            return tag;
        }
    }
    
    private String field;
    private DSLGeoBox geoBox;
    private Boolean cache;
    private Type type;

    public DSLGeoBoundingBoxFilter(String field, DSLGeoBox geoBox) {
        this.field = field;
        this.geoBox = geoBox;
        this.type = Type.Memory;
    }
    
    public DSLGeoBoundingBoxFilter setCache(Boolean cache) {
        this.cache = cache;
        return this;
    }
    
    public DSLGeoBoundingBoxFilter setType(Type type) {
        this.type = type;
        return this;
    }
    
    public JsonObject getFilterAsJson() {
        JsonObject result = new JsonObject();
        JsonObject fieldObject = new JsonObject();
        result.add(GEO_BOUNDING_BOX, fieldObject);
        fieldObject.add(field, geoBox.getBoxAsJsonObject());
        if(type != Type.Memory) {
            fieldObject.add(TYPE, new JsonPrimitive(type.getTag()));
        }
        if(cache != null) {
            fieldObject.add(CACHE, new JsonPrimitive(cache));
        }
        return result;
    }
}
