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
package tellier.es.dsl.query.builder;

import org.junit.Test;
import tellier.es.dsl.query.builder.filter.DSLRangeFilter;
import tellier.es.dsl.query.builder.query.DSLMatchQuery;

import static org.junit.Assert.assertEquals;

/**
 * Some basic tests for DSLBuilder
 */
public class DSLBuilderTest {

    @Test
    public void testDefaultBuilder() {
        DSLBuilder dslBuilder = new DSLBuilder();
        assertEquals(null, dslBuilder.build());
    }

    @Test
    public void testFilterOnly() {
        DSLBuilder dslBuilder = new DSLBuilder().setFilter(new DSLRangeFilter("visitors").lt(72));
        assertEquals("{\"query\":{\"filtered\":{\"query\":{\"match_all\":{}},\"filter\":{\"range\":{\"visitors\":{\"lt\":72}}}}}}", dslBuilder.build().toString());
    }

    @Test
    public void testQueryOnly() {
        DSLBuilder dslBuilder = new DSLBuilder().setQuery(new DSLMatchQuery("user","benwa"));
        assertEquals("{\"query\":{\"match\":{\"user\":\"benwa\"}}}", dslBuilder.build().toString());
    }

    @Test
    public void testBuilder() {
        DSLBuilder dslBuilder = new DSLBuilder().setQuery(new DSLMatchQuery("user","benwa")).setFilter(new DSLRangeFilter("visitors").lt(72));
        assertEquals("{\"query\":{\"filtered\":{\"query\":{\"match\":{\"user\":\"benwa\"}},\"filter\":{\"range\":{\"visitors\":{\"lt\":72}}}}}}", dslBuilder.build().toString());
    }
}
