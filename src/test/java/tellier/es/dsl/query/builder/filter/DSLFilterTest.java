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

import org.junit.Test;
import tellier.es.dsl.query.builder.Utilities.DSLDistance;
import tellier.es.dsl.query.builder.Utilities.DSLGeoBox;
import tellier.es.dsl.query.builder.Utilities.DSLGeoPoint;
import tellier.es.dsl.query.builder.query.DSLRangeQuery;
import tellier.es.dsl.query.builder.query.DSLTermQuery;

import static org.junit.Assert.assertEquals;

/**
 * Basic test on DSLFilters
 */
public class DSLFilterTest {


    @Test
    public void TestRangeFilterLte() {
        DSLFilter filter = new DSLRangeFilter("visitors").lte(1000);
        assertEquals("{\"range\":{\"visitors\":{\"lte\":1000}}}", filter.getFilterAsJson().toString());
    }

    @Test
    public void TestRangeFilterGte() {
        DSLFilter filter = new DSLRangeFilter("visitors").gte(1000);
        assertEquals("{\"range\":{\"visitors\":{\"gte\":1000}}}", filter.getFilterAsJson().toString());
    }

    @Test
    public void TestRangeFilterStringLteGte() {
        DSLFilter filter = new DSLRangeFilter("visitors").lte(1000).gte(50);
        assertEquals("{\"range\":{\"visitors\":{\"gte\":50,\"lte\":1000}}}", filter.getFilterAsJson().toString());
    }

    @Test
    public void TestRangeFilterStringLt() {
        DSLFilter filter = new DSLRangeFilter("date").lt("2014-01-02");
        assertEquals("{\"range\":{\"date\":{\"lt\":\"2014-01-02\"}}}", filter.getFilterAsJson().toString());
    }

    @Test
    public void TestRangeFilterStringGt() {
        DSLFilter filter = new DSLRangeFilter("date").gt("2014-01-02");
        assertEquals("{\"range\":{\"date\":{\"gt\":\"2014-01-02\"}}}", filter.getFilterAsJson().toString());
    }

    @Test
    public void TestRangeFilterStringLte() {
        DSLFilter filter = new DSLRangeFilter("date").lte("2014-01-02");
        assertEquals("{\"range\":{\"date\":{\"lte\":\"2014-01-02\"}}}", filter.getFilterAsJson().toString());
    }

    @Test
    public void TestRangeFilterStringGte() {
        DSLFilter filter = new DSLRangeFilter("date").gte("2014-01-02");
        assertEquals("{\"range\":{\"date\":{\"gte\":\"2014-01-02\"}}}", filter.getFilterAsJson().toString());
    }

    @Test
    public void TestRangeFilterLteGte() {
        DSLFilter filter = new DSLRangeFilter("visitors").lte(1000).gte(50);
        assertEquals("{\"range\":{\"visitors\":{\"gte\":50,\"lte\":1000}}}", filter.getFilterAsJson().toString());
    }

    @Test
    public void TestRangeFilterLt() {
        DSLFilter filter = new DSLRangeFilter("visitors").lt(1000);
        assertEquals("{\"range\":{\"visitors\":{\"lt\":1000}}}", filter.getFilterAsJson().toString());
    }

    @Test
    public void TestRangeFilterGt() {
        DSLFilter filter = new DSLRangeFilter("visitors").gt(1000);
        assertEquals("{\"range\":{\"visitors\":{\"gt\":1000}}}", filter.getFilterAsJson().toString());
    }

    @Test
    public void TestTermFilterString() {
        DSLFilter filter = new DSLTermFilter("user").stringFilter("benwa");
        assertEquals("{\"term\":{\"user\":\"benwa\"}}", filter.getFilterAsJson().toString());
    }

    @Test
    public void TestTermFilterNumber() {
        DSLFilter filter = new DSLTermFilter("id").numberFilter(1024);
        assertEquals("{\"term\":{\"id\":1024}}", filter.getFilterAsJson().toString());
    }

    @Test
    public void TestTermFilterBoolean() {
        DSLFilter filter = new DSLTermFilter("flag").boolFilter(false);
        assertEquals("{\"term\":{\"flag\":false}}", filter.getFilterAsJson().toString());
    }

    @Test
    public void TestTermFilterStringWithoutCache() {
        DSLFilter filter = new DSLTermFilter("user", false).stringFilter("benwa");
        assertEquals("{\"term\":{\"user\":\"benwa\"},\"_cache\":false}", filter.getFilterAsJson().toString());
    }

    @Test
    public void testAndFilter() {
        DSLFilter subFilter1 = new DSLTermFilter("flag").boolFilter(false);
        DSLFilter subFilter2 = new DSLRangeFilter("visitors").gte(50);
        DSLAndFilter filter = new DSLAndFilter();
        filter.addFilter(subFilter1);
        filter.addFilter(subFilter2);
        assertEquals("{\"and\":[{\"term\":{\"flag\":false}},{\"range\":{\"visitors\":{\"gte\":50}}}]}", filter.getFilterAsJson().toString());
    }

    @Test
    public void testOrFilter() {
        DSLFilter subFilter1 = new DSLTermFilter("flag").boolFilter(false);
        DSLFilter subFilter2 = new DSLRangeFilter("visitors").gte(50);
        DSLOrFilter filter = new DSLOrFilter();
        filter.addFilter(subFilter1);
        filter.addFilter(subFilter2);
        assertEquals("{\"or\":[{\"term\":{\"flag\":false}},{\"range\":{\"visitors\":{\"gte\":50}}}]}", filter.getFilterAsJson().toString());
    }

    @Test
    public void testNotFilter() {
        DSLFilter subFilter = new DSLTermFilter("flag").boolFilter(false);
        DSLFilter filter = new DSLNotFilter(subFilter);
        assertEquals("{\"not\":{\"term\":{\"flag\":false}}}", filter.getFilterAsJson().toString());
    }

    @Test
    public void testNestedFilter() {
        DSLFilter subFilter = new DSLTermFilter("blog.user").stringFilter("benwa");
        DSLNestedFilter nestedFilter = new DSLNestedFilter("blog", subFilter);
        assertEquals("{\"nested\":{\"path\":\"blog\",\"filter\":{\"term\":{\"blog.user\":\"benwa\"}}}}", nestedFilter.getFilterAsJson().toString());
    }

    @Test
    public void testExistFilter() {
        DSLFilter dslFilter = new DSLExistFilter("user");
        assertEquals("{\"exists\":{\"field\":\"user\"}}", dslFilter.getFilterAsJson().toString());
    }

    @Test
    public void testExistFilterNullValue() {
        DSLFilter dslFilter = new DSLExistFilter("user").setNullValue("@_@");
        assertEquals("{\"exists\":{\"field\":\"user\",\"null_value\":\"@_@\"}}", dslFilter.getFilterAsJson().toString());
    }
    
    @Test
    public void geoBoundingBoxTest() {
        DSLGeoBoundingBoxFilter filter = new DSLGeoBoundingBoxFilter("pin.location", new DSLGeoBox(new DSLGeoPoint(40.73, -74.1), new DSLGeoPoint(40.01, -71.12)));
        assertEquals("{\"geo_bounding_box\":{\"pin.location\":{\"top_left\":{\"lat\":40.73,\"lon\":-74.1},\"bottom_right\":{\"lat\":40.01,\"lon\":-71.12}}}}", filter.getFilterAsJson().toString());
        filter = new DSLGeoBoundingBoxFilter("pin.location", new DSLGeoBox(new DSLGeoPoint(DSLGeoPoint.Mode.Array, 40.73, -74.1), new DSLGeoPoint(DSLGeoPoint.Mode.Array, 40.01, -71.12)));
        assertEquals("{\"geo_bounding_box\":{\"pin.location\":{\"top_left\":[-74.1,40.73],\"bottom_right\":[-71.12,40.01]}}}", filter.getFilterAsJson().toString());
        filter = new DSLGeoBoundingBoxFilter("pin.location", new DSLGeoBox(new DSLGeoPoint(DSLGeoPoint.Mode.LatLon, 40.73, -74.1), new DSLGeoPoint(DSLGeoPoint.Mode.LatLon, 40.01, -71.12)));
        assertEquals("{\"geo_bounding_box\":{\"pin.location\":{\"top_left\":\"40.73, -74.1\",\"bottom_right\":\"40.01, -71.12\"}}}", filter.getFilterAsJson().toString());
        filter = new DSLGeoBoundingBoxFilter("pin.location", new DSLGeoBox(new DSLGeoPoint("dr5r9ydj2y73"), new DSLGeoPoint("drj7teegpus6")) );
        assertEquals("{\"geo_bounding_box\":{\"pin.location\":{\"top_left\":\"dr5r9ydj2y73\",\"bottom_right\":\"drj7teegpus6\"}}}", filter.getFilterAsJson().toString());
        filter = new DSLGeoBoundingBoxFilter("pin.location", new DSLGeoBox(-74.1, 40.73, -71.12, 40.01) );
        assertEquals("{\"geo_bounding_box\":{\"pin.location\":{\"top\":-74.1,\"bottom\":-71.12,\"right\":40.01,\"left\":40.73}}}", filter.getFilterAsJson().toString());
        filter = new DSLGeoBoundingBoxFilter("pin.location", new DSLGeoBox(new DSLGeoPoint(40.73, -74.1), new DSLGeoPoint(40.01, -71.12))).setType(DSLGeoBoundingBoxFilter.Type.Indexed);
        assertEquals("{\"geo_bounding_box\":{\"pin.location\":{\"top_left\":{\"lat\":40.73,\"lon\":-74.1},\"bottom_right\":{\"lat\":40.01,\"lon\":-71.12}},\"type\":\"indexed\"}}", filter.getFilterAsJson().toString());
    }
    
    @Test
    public void geoDistanceTest() {
        DSLGeoDistanceFilter filter = new DSLGeoDistanceFilter("pin.location", new DSLGeoPoint(40,-70), new DSLDistance(12, DSLDistance.Unit.Kilometer));
        assertEquals("{\"geo_distance\":{\"distance\":\"12km\",\"pin.location\":{\"lat\":40.0,\"lon\":-70.0}}}", filter.getFilterAsJson().toString());
    }
    
    @Test
    public void geoDistanceRangeTest() {
        DSLGeoDistanceRangeFilter filter = new DSLGeoDistanceRangeFilter("pin.location", new DSLDistance(200, DSLDistance.Unit.Kilometer), new DSLDistance(400, DSLDistance.Unit.Kilometer), new DSLGeoPoint(40.0, -70.0));
        assertEquals("{\"geo_distance_range\":{\"from\":\"200km\",\"to\":\"400km\",\"pin.location\":{\"lat\":40.0,\"lon\":-70.0}}}", filter.getFilterAsJson().toString());
    }
    
    @Test
    public void geoPolygonTest() {
        DSLGeoPolygonFilter filter = new DSLGeoPolygonFilter("person.location").addPoint(new DSLGeoPoint(40, -70)).addPoint(new DSLGeoPoint(30, -80)).addPoint(new DSLGeoPoint(20, -90));
        assertEquals("{\"geo_polygon\":{\"person.location\":{\"points\":[{\"lat\":40.0,\"lon\":-70.0},{\"lat\":30.0,\"lon\":-80.0},{\"lat\":20.0,\"lon\":-90.0}]}}}", filter.getFilterAsJson().toString());
    }
    
    @Test
    public void hasChildTest() {
        DSLHasChildFilter filter = new DSLHasChildFilter("blog_tag", new DSLTermQuery("tag", "something"));
        assertEquals("{\"has_child\":{\"type\":\"blog_tag\",\"query\":{\"term\":{\"tag\":\"something\"}}}}", filter.getFilterAsJson().toString());
        filter.setMaxChildren(10).setMinChildren(2);
        assertEquals("{\"has_child\":{\"type\":\"blog_tag\",\"query\":{\"term\":{\"tag\":\"something\"}},\"max_children\":10,\"min_children\":2}}", filter.getFilterAsJson().toString());
    }
    
    @Test
    public void hasParentTest() {
        DSLHasParentFilter filter = new DSLHasParentFilter("blog", new DSLTermQuery("tag", "something"));
        assertEquals("{\"has_parent\":{\"parent_type\":\"blog\",\"query\":{\"term\":{\"tag\":\"something\"}}}}", filter.getFilterAsJson().toString());
    }
    
    @Test
    public void idsTest() {
        DSLIdsFilter filter = new DSLIdsFilter().addType("my_type").addValue("1").addValue("4").addValue("100");
        assertEquals("{\"ids\":{\"type\":\"my_type\",\"values\":[\"1\",\"4\",\"100\"]}}", filter.getFilterAsJson().toString());
    }
    
    @Test
    public void indicesTest() {
        DSLIndicesFilter filter = new DSLIndicesFilter(new DSLExistFilter("tag")).addIndice("index1").addIndice("index2").setNoMatchFilter(new DSLHasChildFilter("blog", new DSLRangeQuery("date").lte("now").gte("2012/01/01 00:00:00")));
        assertEquals("{\"indices\":{\"indices\":[\"index1\",\"index2\"],\"filter\":{\"exists\":{\"field\":\"tag\"}},\"no_match_filter\":{\"has_child\":{\"type\":\"blog\",\"query\":{\"range\":{\"date\":{\"gte\":\"2012/01/01 00:00:00\",\"lte\":\"now\"}}}}}}}", filter.getFilterAsJson().toString());
    }
    
    @Test
    public void limitTest() {
        DSLLimitFilter filter = new DSLLimitFilter(100);
        assertEquals("{\"limit\":{\"value\":100}}", filter.getFilterAsJson().toString());
    }
    
    @Test
    public void matchAllTest() {
        DSLMatchAllFilter filter = new DSLMatchAllFilter();
        assertEquals("{\"match_all\":{}}", filter.getFilterAsJson().toString());
    }
    
    @Test
    public void missingTest() {
        DSLMissingFilter filter = new DSLMissingFilter("user");
        assertEquals("{\"missing\":{\"field\":\"user\"}}", filter.getFilterAsJson().toString());
        filter.setExistence(true).setNullValue(false);
        assertEquals("{\"missing\":{\"field\":\"user\",\"existence\":true,\"null_value\":false}}", filter.getFilterAsJson().toString());
    }
}
