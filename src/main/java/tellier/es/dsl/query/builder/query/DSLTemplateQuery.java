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

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.List;

/**
 * Template query
 *
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-template-query.html
 */
public class DSLTemplateQuery implements DSLQuery {

    private static final String TEMPLATE = "template";
    private static final String PARAMS = "params";
    private static final String FILE = "file";
    private static final String ID = "id";

    private String id;
    private String file;
    private DSLQuery subQuery;
    private List<Param> params = new ArrayList<Param>();
    private Mode mode;

    public class Param {
        private String name;
        private String value;

        public Param(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }

    enum Mode {
        Id,
        File,
        Provided,
        None
    }

    public DSLTemplateQuery addParam(String name, String value) {
        params.add(new Param(name, value));
        return this;
    }

    public DSLTemplateQuery setId(String id) {
        this.id = id;
        this.mode = Mode.Id;
        return this;
    }

    public DSLTemplateQuery setSubQuery(DSLQuery subQuery) {
        this.subQuery = subQuery;
        this.mode = Mode.Provided;
        return this;
    }

    public DSLTemplateQuery setFile(String file) {
        this.file = file;
        this.mode = Mode.File;
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject result = new JsonObject();
        JsonObject templateObject = new JsonObject();
        JsonObject paramsObject = new JsonObject();
        result.add(TEMPLATE, templateObject);
        templateObject.add(PARAMS, paramsObject);
        for(Param param : params) {
            paramsObject.add(param.getName(), new JsonPrimitive(param.getValue()));
        }
        switch (mode) {
            case File :
                templateObject.add(FILE, new JsonPrimitive(file));
                break;
            case Id:
                templateObject.add(ID, new JsonPrimitive(id));
                break;
            case Provided:
                templateObject.add(QUERY, subQuery.getQueryAsJson());
                break;
        }
        return result;
    }


}
