package tellier.es.dsl.query.builder.query;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for multi match query
 */
public class MultiMatchTest {

    @Test
    public void basicTest() {
        DSLMultiMatchQuery dslMultiMatchQuery = new DSLMultiMatchQuery("A pattern to search").addField("body").addField("header");
        assertEquals("{\"multi_match\":{\"query\":\"A pattern to search\",\"fields\":[\"body\",\"header\"]}}", dslMultiMatchQuery.getQueryAsJson().toString());
    }

    @Test
    public  void crossFieldsTest() {
        DSLMultiMatchQuery dslMultiMatchQuery = new DSLMultiMatchQuery("A pattern to search").addField("body").addField("header").setType(DSLMultiMatchQuery.Type.CROSS_FIELDS);
        assertEquals("{\"multi_match\":{\"query\":\"A pattern to search\",\"fields\":[\"body\",\"header\"],\"type\":\"cross_fields\"}}", dslMultiMatchQuery.getQueryAsJson().toString());
    }

    @Test
    public  void mostFieldsTest() {
        DSLMultiMatchQuery dslMultiMatchQuery = new DSLMultiMatchQuery("A pattern to search").addField("body").addField("header").setType(DSLMultiMatchQuery.Type.MOST_FIELDS);
        assertEquals("{\"multi_match\":{\"query\":\"A pattern to search\",\"fields\":[\"body\",\"header\"],\"type\":\"most_fields\"}}", dslMultiMatchQuery.getQueryAsJson().toString());
    }

    @Test
    public  void phraseTest() {
        DSLMultiMatchQuery dslMultiMatchQuery = new DSLMultiMatchQuery("A pattern to search").addField("body").addField("header").setType(DSLMultiMatchQuery.Type.PHRASE);
        assertEquals("{\"multi_match\":{\"query\":\"A pattern to search\",\"fields\":[\"body\",\"header\"],\"type\":\"phrase\"}}", dslMultiMatchQuery.getQueryAsJson().toString());
    }

    @Test
    public void phrasePrefixTest() {
        DSLMultiMatchQuery dslMultiMatchQuery = new DSLMultiMatchQuery("A pattern to search").addField("body").addField("header").setType(DSLMultiMatchQuery.Type.PHRASE_PREFIX);
        assertEquals("{\"multi_match\":{\"query\":\"A pattern to search\",\"fields\":[\"body\",\"header\"],\"type\":\"phrase_prefix\"}}", dslMultiMatchQuery.getQueryAsJson().toString());
    }

    @Test
    public void useDisMaxTest() {
        DSLMultiMatchQuery dslMultiMatchQuery = new DSLMultiMatchQuery("A pattern to search").addField("body").addField("header").setUseDisMax(false);
        assertEquals("{\"multi_match\":{\"query\":\"A pattern to search\",\"fields\":[\"body\",\"header\"],\"use_dis_max\":false}}", dslMultiMatchQuery.getQueryAsJson().toString());
    }

    @Test
    public void tieBreaker() {
        DSLMultiMatchQuery dslMultiMatchQuery = new DSLMultiMatchQuery("A pattern to search").addField("body").addField("header").setTieBreaker(0.0002);
        // Valid even though it is in exponential notation. Elastic search can parse that.
        assertEquals("{\"multi_match\":{\"query\":\"A pattern to search\",\"fields\":[\"body\",\"header\"],\"tie_breaker\":2.0E-4}}", dslMultiMatchQuery.getQueryAsJson().toString());
    }

    // Stuff from MatchUtilities was tested in AdvancedMatchQueryTest
}
