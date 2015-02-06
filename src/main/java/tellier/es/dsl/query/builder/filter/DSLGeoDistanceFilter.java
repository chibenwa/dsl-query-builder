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
 * Represents a Geo distance filter
 * 
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-geo-distance-filter.html
 */
public class DSLGeoDistanceFilter implements DSLFilter {
    
    private static final String GEO_DISTANCE = "geo_distance";
    private static final String DISTANCE = "distance";
    private static final String CACHE = "_cache";
    private static final String DISTANCE_TYPE = "distance_type";
    private static final String OPTIMIZE_BBOX = "optimize_bbox";
    
    private String field;
    private DSLGeoPoint point;
    private DSLDistance distance;
    private DistanceType distanceType;
    private OptimizeBbox optimizeBbox;
    private Boolean cache;

    public DSLGeoDistanceFilter(String field, DSLGeoPoint point, DSLDistance distance) {
        this.field = field;
        this.point = point;
        this.distance = distance;
        this.distanceType = DistanceType.SloppyArc;
        this.optimizeBbox = OptimizeBbox.Memory;
    }
    
    public enum DistanceType {
        SloppyArc("sloppy_arc"),
        Arc("arc"),
        Plain("plain");
        
        private String tag;
        
        DistanceType(String tag) {
            this.tag = tag;
        }
        
        public String getTag() {
            return tag;
        }
    }
    
    public enum OptimizeBbox {
        Memory("memory"),
        Indexed("indexed"),
        None("none");

        private String tag;

        OptimizeBbox(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }
    }

    public DSLGeoDistanceFilter setDistanceType(DistanceType distanceType) {
        this.distanceType = distanceType;
        return this;
    }

    public DSLGeoDistanceFilter setOptimizeBbox(OptimizeBbox optimizeBbox) {
        this.optimizeBbox = optimizeBbox;
        return this;
    }

    public DSLGeoDistanceFilter setCache(Boolean cache) {
        this.cache = cache;
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject result = new JsonObject();
        JsonObject geoDistanceObject = new JsonObject();
        result.add(GEO_DISTANCE, geoDistanceObject);
        geoDistanceObject.add(DISTANCE, new JsonPrimitive(distance.toString()));
        geoDistanceObject.add(field, point.getPointAsJsonElement());
        if(cache != null) {
            geoDistanceObject.add(CACHE, new JsonPrimitive(cache));
        }
        if(distanceType != DistanceType.SloppyArc) {
            geoDistanceObject.add(DISTANCE_TYPE, new JsonPrimitive(distanceType.getTag()));
        }
        if(optimizeBbox != OptimizeBbox.Memory) {
            geoDistanceObject.add(OPTIMIZE_BBOX, new JsonPrimitive(optimizeBbox.getTag()));
        }
        return result;
    }
}
