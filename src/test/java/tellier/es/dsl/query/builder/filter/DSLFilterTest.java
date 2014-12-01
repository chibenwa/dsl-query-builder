package tellier.es.dsl.query.builder.filter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Basic test on DSLFilters
 */
public class DSLFilterTest {


    @Test
    public void TestRangeFilterLte() {
        DSLFilter filter = new DSLRangeFilter("visitors").lte(1000);
        assertEquals("{\"range\":{\"visitors\":{\"lte\":1000}}}", filter.getQueryAsJson().toString());
    }

    @Test
    public void TestRangeFilterGte() {
        DSLFilter filter = new DSLRangeFilter("visitors").gte(1000);
        assertEquals("{\"range\":{\"visitors\":{\"gte\":1000}}}", filter.getQueryAsJson().toString());
    }

    @Test
    public void TestRangeFilterStringLteGte() {
        DSLFilter filter = new DSLRangeFilter("visitors").lte(1000).gte(50);
        assertEquals("{\"range\":{\"visitors\":{\"gte\":50,\"lte\":1000}}}", filter.getQueryAsJson().toString());
    }

    @Test
    public void TestRangeFilterStringLt() {
        DSLFilter filter = new DSLRangeFilter("date").lt("2014-01-02");
        assertEquals("{\"range\":{\"date\":{\"lt\":\"2014-01-02\"}}}", filter.getQueryAsJson().toString());
    }

    @Test
    public void TestRangeFilterStringGt() {
        DSLFilter filter = new DSLRangeFilter("date").gt("2014-01-02");
        assertEquals("{\"range\":{\"date\":{\"gt\":\"2014-01-02\"}}}", filter.getQueryAsJson().toString());
    }

    @Test
    public void TestRangeFilterStringLte() {
        DSLFilter filter = new DSLRangeFilter("date").lte("2014-01-02");
        assertEquals("{\"range\":{\"date\":{\"lte\":\"2014-01-02\"}}}", filter.getQueryAsJson().toString());
    }

    @Test
    public void TestRangeFilterStringGte() {
        DSLFilter filter = new DSLRangeFilter("date").gte("2014-01-02");
        assertEquals("{\"range\":{\"date\":{\"gte\":\"2014-01-02\"}}}", filter.getQueryAsJson().toString());
    }

    @Test
    public void TestRangeFilterLteGte() {
        DSLFilter filter = new DSLRangeFilter("visitors").lte(1000).gte(50);
        assertEquals("{\"range\":{\"visitors\":{\"gte\":50,\"lte\":1000}}}", filter.getQueryAsJson().toString());
    }

    @Test
    public void TestRangeFilterLt() {
        DSLFilter filter = new DSLRangeFilter("visitors").lt(1000);
        assertEquals("{\"range\":{\"visitors\":{\"lt\":1000}}}", filter.getQueryAsJson().toString());
    }

    @Test
    public void TestRangeFilterGt() {
        DSLFilter filter = new DSLRangeFilter("visitors").gt(1000);
        assertEquals("{\"range\":{\"visitors\":{\"gt\":1000}}}", filter.getQueryAsJson().toString());
    }

    @Test
    public void TestTermFilterString() {
        DSLFilter filter = new DSLTermFilter("user").stringFilter("benwa");
        assertEquals("{\"term\":{\"user\":\"benwa\"}}", filter.getQueryAsJson().toString());
    }

    @Test
    public void TestTermFilterNumber() {
        DSLFilter filter = new DSLTermFilter("id").numberFilter(1024);
        assertEquals("{\"term\":{\"id\":1024}}", filter.getQueryAsJson().toString());
    }

    @Test
    public void TestTermFilterBoolean() {
        DSLFilter filter = new DSLTermFilter("flag").boolFilter(false);
        assertEquals("{\"term\":{\"flag\":false}}", filter.getQueryAsJson().toString());
    }

    @Test
    public void TestTermFilterStringWithoutCache() {
        DSLFilter filter = new DSLTermFilter("user", false).stringFilter("benwa");
        assertEquals("{\"term\":{\"user\":\"benwa\"},\"_cache\":false}", filter.getQueryAsJson().toString());
    }

    @Test
    public void testAndFilter() {
        DSLFilter subFilter1 = new DSLTermFilter("flag").boolFilter(false);
        DSLFilter subFilter2 = new DSLRangeFilter("visitors").gte(50);
        DSLAndFilter filter = new DSLAndFilter();
        filter.addFilter(subFilter1);
        filter.addFilter(subFilter2);
        assertEquals("{\"and\":[{\"term\":{\"flag\":false}},{\"range\":{\"visitors\":{\"gte\":50}}}]}", filter.getQueryAsJson().toString());
    }

    @Test
    public void testOrFilter() {
        DSLFilter subFilter1 = new DSLTermFilter("flag").boolFilter(false);
        DSLFilter subFilter2 = new DSLRangeFilter("visitors").gte(50);
        DSLOrFilter filter = new DSLOrFilter();
        filter.addFilter(subFilter1);
        filter.addFilter(subFilter2);
        assertEquals("{\"or\":[{\"term\":{\"flag\":false}},{\"range\":{\"visitors\":{\"gte\":50}}}]}", filter.getQueryAsJson().toString());
    }

    @Test
    public void testNotFilter() {
        DSLFilter subFilter = new DSLTermFilter("flag").boolFilter(false);
        DSLFilter filter = new DSLNotFilter(subFilter);
        assertEquals("{\"not\":{\"term\":{\"flag\":false}}}", filter.getQueryAsJson().toString());
    }

    @Test
    public void testNestedFilter() {
        DSLFilter subFilter = new DSLTermFilter("blog.user").stringFilter("benwa");
        DSLNestedFilter nestedFilter = new DSLNestedFilter("blog", subFilter);
        assertEquals("{\"nested\":{\"path\":\"blog\",\"filter\":{\"term\":{\"blog.user\":\"benwa\"}}}}", nestedFilter.getQueryAsJson().toString());
    }

    @Test
    public void testExistFilter() {
        DSLFilter dslFilter = new DSLExistFilter("user");
        assertEquals("{\"exists\":{\"field\":\"user\"}}", dslFilter.getQueryAsJson().toString());
    }

    @Test
    public void testExistFilterNullValue() {
        DSLFilter dslFilter = new DSLExistFilter("user").setNullValue("@_@");
        assertEquals("{\"exists\":{\"field\":\"user\",\"null_value\":\"@_@\"}}", dslFilter.getQueryAsJson().toString());
    }
}
