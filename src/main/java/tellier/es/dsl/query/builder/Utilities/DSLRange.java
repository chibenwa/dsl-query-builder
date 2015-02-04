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
 * Code factorisation between DSLRangeFilter and DSLRangeQuery
 */
public class DSLRange {

    private static final String GTE = "gte";
    private static final String LTE = "lte";
    private static final String GT = "gt";
    private static final String LT = "lt";
    private static final String RANGE = "range";
    private static final String TIME_ZONE = "time_zone";
    private static final String BOOST = "boost";

    private String field;
    private Number gtNumberValue;
    private Number ltNumberValue;
    private String gtString;
    private String ltString;
    private Comparator gtComparator;
    private Comparator ltComparator;
    private Type type;
    private String timeZone;
    private Double boost;

    enum Comparator {
        STRICT,
        LAXIST
    }

    enum Type {
        NUMBER,
        STRING
    }

    public DSLRange(String field) {
        this.field = field;
        this.gtNumberValue = null;
        this.ltNumberValue = null;
        this.type = Type.NUMBER;
    }

    public void gte(Number value) {
        type = Type.NUMBER;
        gtNumberValue = value;
        gtComparator = Comparator.LAXIST;
    }

    public void lte(Number value) {
        type = Type.NUMBER;
        ltNumberValue = value;
        ltComparator = Comparator.LAXIST;
    }

    public void gt(Number value) {
        type = Type.NUMBER;
        gtNumberValue = value;
        gtComparator = Comparator.STRICT;
    }

    public void lt(Number value) {
        type = Type.NUMBER;
        ltNumberValue = value;
        ltComparator = Comparator.STRICT;
    }

    public void gte(String string) {
        type = Type.STRING;
        gtString = string;
        gtComparator = Comparator.LAXIST;
    }

    public void lte(String string) {
        type = Type.STRING;
        ltString = string;
        ltComparator = Comparator.LAXIST;
    }

    public void gt(String string) {
        type = Type.STRING;
        gtString = string;
        gtComparator = Comparator.STRICT;
    }

    public void lt(String string) {
        type = Type.STRING;
        ltString = string;
        ltComparator = Comparator.STRICT;
    }

    public void setBoost(Double boost) {
        this.boost = boost;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public JsonObject getAsJson() {
        if( field == null ) {
            return null;
        }
        JsonObject conditions = getRangeCondition();
        if(boost != null) {
            conditions.add(BOOST, new JsonPrimitive(boost));
        }
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
        if(timeZone != null) {
            conditions.add(TIME_ZONE, new JsonPrimitive(timeZone));
        }
        return conditions;
    }
}
