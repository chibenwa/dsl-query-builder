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

/**
 * Represents a RangeFilter
 */
public class DSLRangeFilter implements DSLFilter{
    private String field;
    private Number gtNumberValue;
    private Number ltNumberValue;
    private String gtString;
    private String ltString;
    private Comparator gtComparator;
    private Comparator ltComparator;
    private Type type;

    enum Comparator {
        STRICT,
        LAXIST
    }

    enum Type {
        NUMBER,
        STRING
    }

    public DSLRangeFilter(String field) {
        this.field = field;
        this.gtNumberValue = null;
        this.ltNumberValue = null;
        this.type = Type.NUMBER;
    }

    public DSLRangeFilter gte(Number value) {
        type = Type.NUMBER;
        gtNumberValue = value;
        gtComparator = Comparator.LAXIST;
        return this;
    }

    public DSLRangeFilter lte(Number value) {
        type = Type.NUMBER;
        ltNumberValue = value;
        ltComparator = Comparator.LAXIST;
        return this;
    }

    public DSLRangeFilter gt(Number value) {
        type = Type.NUMBER;
        gtNumberValue = value;
        gtComparator = Comparator.STRICT;
        return this;
    }

    public DSLRangeFilter lt(Number value) {
        type = Type.NUMBER;
        ltNumberValue = value;
        ltComparator = Comparator.STRICT;
        return this;
    }

    public DSLRangeFilter gte(String string) {
        type = Type.STRING;
        gtString = string;
        gtComparator = Comparator.LAXIST;
        return this;
    }

    public DSLRangeFilter lte(String string) {
        type = Type.STRING;
        ltString = string;
        ltComparator = Comparator.LAXIST;
        return this;
    }

    public DSLRangeFilter gt(String string) {
        type = Type.STRING;
        gtString = string;
        gtComparator = Comparator.STRICT;
        return this;
    }

    public DSLRangeFilter lt(String string) {
        type = Type.STRING;
        ltString = string;
        ltComparator = Comparator.STRICT;
        return this;
    }

    public JsonObject getQueryAsJson() {
        if( field == null ) {
            return null;
        }
        JsonObject conditions = getRangeCondition();
        JsonObject rangeBody = new JsonObject();
        rangeBody.add(field, conditions);
        JsonObject rangeObject = new JsonObject();
        rangeObject.add(RANGE, rangeBody);
        return rangeObject;
    }

    private JsonObject getRangeCondition() {
        JsonObject conditions = new JsonObject();
        switch (type) {
            case NUMBER:
                return getNumericCondition(conditions);
            case STRING:
                return getStringCondition(conditions);
            default:
                throw new RuntimeException();
        }

    }

    private JsonObject getNumericCondition(JsonObject conditions) {
        if( gtNumberValue == null && ltNumberValue == null ) {
            return null;
        }
        if(gtNumberValue != null) {
            if(gtComparator == Comparator.STRICT) {
                conditions.add(GT, new JsonPrimitive(gtNumberValue));
            } else {
                conditions.add(GTE, new JsonPrimitive(gtNumberValue));
            }
        }
        if(ltNumberValue != null) {
            if(ltComparator == Comparator.STRICT) {
                conditions.add(LT, new JsonPrimitive(ltNumberValue));
            } else {
                conditions.add(LTE, new JsonPrimitive(ltNumberValue));
            }
        }
        return conditions;
    }

    private JsonObject getStringCondition(JsonObject conditions) {
        if( gtString == null && ltString == null ) {
            return null;
        }
        if(gtString != null) {
            if(gtComparator == Comparator.STRICT) {
                conditions.add(GT, new JsonPrimitive(gtString));
            } else {
                conditions.add(GTE, new JsonPrimitive(gtString));
            }
        }
        if(ltString != null) {
            if(ltComparator == Comparator.STRICT) {
                conditions.add(LT, new JsonPrimitive(ltString));
            } else {
                conditions.add(LTE, new JsonPrimitive(ltString));
            }
        }
        return conditions;
    }
}
