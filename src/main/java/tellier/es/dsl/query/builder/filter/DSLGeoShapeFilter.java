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
import tellier.es.dsl.query.builder.Utilities.shape.DSLShape;

/**
 * Represents a Geo shape filter
 * 
 * See  http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-geo-shape-filter.html
 */
public class DSLGeoShapeFilter implements DSLFilter {

    private final static String GEO_SHAPE = "geo_shape";
    private final static String SHAPE = "shape";
    private final static String INDEXED_SHAPE = "indexed_shape";
    private final static String INDEX = "index";
    private final static String TYPE = "type";
    private final static String ID = "id";
    private final static String PATH = "path";

    private DSLShape shape;
    private String field;
    private boolean isStored;

    private String id;
    private String index;
    private String type;
    private String path;

    public DSLGeoShapeFilter(String field, String id, String index, String type, String path) {
        this.field = field;
        this.id = id;
        this.index = index;
        this.type = type;
        this.path = path;
        this.isStored = true;
    }

    public DSLGeoShapeFilter(String field, DSLShape shape) {
        this.shape = shape;
        this.field = field;
        this.isStored = false;
    }

    public JsonObject getQueryAsJson() {
        JsonObject result = new JsonObject();
        JsonObject geoShapeObject = new JsonObject();
        JsonObject locationObject = new JsonObject();
        result.add(GEO_SHAPE, geoShapeObject);
        geoShapeObject.add(field, locationObject);
        if(!isStored) {
            locationObject.add(SHAPE, shape.getShapeAsJson());
        } else {
            JsonObject retrievalObject = new JsonObject();
            retrievalObject.add(INDEX,new JsonPrimitive(index));
            retrievalObject.add(TYPE,new JsonPrimitive(type));
            retrievalObject.add(ID,new JsonPrimitive(id));
            retrievalObject.add(PATH,new JsonPrimitive(path));
            locationObject.add(INDEXED_SHAPE, retrievalObject);
        }
        return result;
    }


}
