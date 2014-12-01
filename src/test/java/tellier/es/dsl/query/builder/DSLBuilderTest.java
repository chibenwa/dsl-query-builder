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
