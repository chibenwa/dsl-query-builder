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
 * Represents a Regex query
 *
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-regexp-query.html
 */
public class DSLRegexQuery implements DSLQuery {

    private final static String REGEXP = "regexp";
    private final static String VALUE = "value";
    private final static String BOOST = "boost";
    private final static String FLAGS = "flags";

    private String field;
    private String regexp;
    private List<Flag> flags = new ArrayList<Flag>();
    private Double boost;

    enum Flag {
        ALL("ALL"),
        ANYSTRING("ANYSTRING"),
        COMPLEMENT("COMPLEMENT"),
        EMPTY("EMPTY"),
        INTERSECTION("INTERSECTION"),
        INTERVAL("INTERVAL"),
        NONE("NONE");

        private String tag;

        Flag(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }
    }

    public DSLRegexQuery(String field, String regexp) {
        this.field = field;
        this.regexp = regexp;
    }

    public DSLRegexQuery addFlag(Flag flag) {
        flags.add(flag);
        return this;
    }

    public DSLRegexQuery setBoost(Double boost) {
        this.boost = boost;
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject result = new JsonObject();
        JsonObject regexpObject = new JsonObject();
        result.add(REGEXP, regexpObject);
        if(flags.size()> 0 || boost != null) {
            JsonObject fieldObject = new JsonObject();
            regexpObject.add(field, fieldObject);
            fieldObject.add(VALUE, new JsonPrimitive(regexp));
            if (flags.size() > 0) {
                StringBuilder flagsStringBuilder = new StringBuilder();
                for (int i = 0; i < flags.size(); i++) {
                    if (i > 0) {
                        flagsStringBuilder.append('|');
                    }
                    flagsStringBuilder.append(flags.get(i).getTag());
                }
                String flagString = flagsStringBuilder.toString();
                fieldObject.add(FLAGS, new JsonPrimitive(flagString));
            }
            if (boost != null) {
                fieldObject.add(BOOST, new JsonPrimitive(boost));
            }
        } else {
            regexpObject.add(field, new JsonPrimitive(regexp));
        }
        return result;
    }
}
