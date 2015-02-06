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

/**
 * Utility for managing distance
 */
public class DSLDistance {
    
    public enum Unit {
        Mile("mi"),
        Yard("yd"),
        Feet("ft"),
        Inch("in"),
        Kilometer("km"),
        Meter("m"),
        Centimeter("cm"),
        Millimeter("mm"),
        NauticalMile("NM");
        
        private String tag;
        
        Unit(String tag) {
            this.tag = tag;
        }
        
        public String getTag() {
            return tag;
        }
    }
    
    private Unit unit;
    private Double distance;
    private Integer distanceInteger;

    public DSLDistance( double distance, Unit unit) {
        this.unit = unit;
        this.distance = distance;
    }

    public DSLDistance(int distance, Unit unit) {
        this.unit = unit;
        this.distanceInteger = distance;
    }
    
    @Override
    public String toString() {
        if(distance != null) {
            return distance + unit.getTag();
        } else {
            return distanceInteger+unit.getTag();
        }
    }
}
