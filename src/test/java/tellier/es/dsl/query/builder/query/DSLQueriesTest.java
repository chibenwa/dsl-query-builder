package tellier.es.dsl.query.builder.query;

import org.junit.Test;
import tellier.es.dsl.query.builder.filter.DSLExistFilter;
import tellier.es.dsl.query.builder.filter.DSLFilter;

import static org.junit.Assert.assertEquals;

/**
 * Basic tests on DSLQueries
 */
public class DSLQueriesTest {

    @Test
    public void testMatchAll() {
        DSLQuery query = new DSLMatchAllQuery();
        assertEquals( "{\"match_all\":{}}", query.getQueryAsJson().toString() );
    }

    @Test
    public void testMatch() {
        DSLQuery query = new DSLMatchQuery("user", "kimchi");
        assertEquals("{\"match\":{\"user\":\"kimchi\"}}", query.getQueryAsJson().toString() );
    }

    @Test
    public void testMust() {
        DSLQuery subQuery1 = new DSLMatchQuery("user", "kimchi");
        DSLQuery subQuery2 = new DSLMatchQuery("job", "intern");
        DSLMustQuery query = new DSLMustQuery();
        query.addQuery(subQuery1);
        query.addQuery(subQuery2);
        assertEquals("{\"bool\":{\"must\":[{\"match\":{\"user\":\"kimchi\"}},{\"match\":{\"job\":\"intern\"}}]}}", query.getQueryAsJson().toString());
    }

    @Test
    public void testShould() {
        DSLQuery subQuery1 = new DSLMatchQuery("user", "kimchi");
        DSLQuery subQuery2 = new DSLMatchQuery("job", "intern");
        DSLShouldQuery query = new DSLShouldQuery();
        query.addQuery(subQuery1);
        query.addQuery(subQuery2);
        assertEquals("{\"bool\":{\"should\":[{\"match\":{\"user\":\"kimchi\"}},{\"match\":{\"job\":\"intern\"}}]}}", query.getQueryAsJson().toString());
    }

    @Test
    public void testMustNot() {
        DSLQuery subQuery1 = new DSLMatchQuery("user", "kimchi");
        DSLQuery subQuery2 = new DSLMatchQuery("job", "intern");
        DSLMustNotQuery query = new DSLMustNotQuery();
        query.addQuery(subQuery1);
        query.addQuery(subQuery2);
        assertEquals("{\"bool\":{\"must_not\":[{\"match\":{\"user\":\"kimchi\"}},{\"match\":{\"job\":\"intern\"}}]}}", query.getQueryAsJson().toString());
    }

    @Test
    public void testNestedQuery() {
        DSLQuery subQuery = new DSLMatchQuery("nested.user", "kimchi");
        DSLQuery query = new DSLNestedQuery("nested", DSLNestedQuery.Score_mode.SUM, subQuery);
        assertEquals("{\"nested\":{\"path\":\"nested\",\"score_mode\":\"sum\",\"query\":{\"match\":{\"nested.user\":\"kimchi\"}}}}", query.getQueryAsJson().toString());
    }

    @Test
    public void constantScoreQueryTest() {
        DSLQuery dslQuery = new DSLMatchQuery("user", "kimchi");
        DSLQuery dslQueryConstant = new DSLConstantScoreQuery(dslQuery,1.2);
        assertEquals("{\"constant_score\":{\"query\":{\"match\":{\"user\":\"kimchi\"}},\"boost\":1.2}}", dslQueryConstant.getQueryAsJson().toString());
        DSLFilter filter = new DSLExistFilter("text");
        dslQueryConstant = new DSLConstantScoreQuery(filter, 0.8);
        System.out.println(dslQueryConstant.getQueryAsJson());
        assertEquals("{\"constant_score\":{\"filter\":{\"exists\":{\"field\":\"text\"}},\"boost\":0.8}}", dslQueryConstant.getQueryAsJson().toString());
    }
}
