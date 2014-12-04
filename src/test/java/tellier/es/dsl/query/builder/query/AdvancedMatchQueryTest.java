package tellier.es.dsl.query.builder.query;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for match queries advanced functionality
 */
public class AdvancedMatchQueryTest {

    @Test
    public void lenientTest() {
        DSLMatchQuery dslMatchQuery = new DSLMatchQuery("field", "value").setLenient(false);
        assertEquals("{\"match\":{\"field\":\"value\"}}", dslMatchQuery.getQueryAsJson().toString());
        dslMatchQuery = new DSLMatchQuery("field", "value").setLenient(true);
        assertEquals("{\"match\":{\"field\":{\"query\":\"value\",\"lenient\":true}}}",dslMatchQuery.getQueryAsJson().toString());
    }

    @Test
    public void analyserTest() {
        DSLMatchQuery dslMatchQuery = new DSLMatchQuery("field", "value").setAnalyser("my_analyzer");
        assertEquals("{\"match\":{\"field\":{\"query\":\"value\",\"analyzer\":\"my_analyzer\"}}}",dslMatchQuery.getQueryAsJson().toString());
    }

    @Test
    public void matchPhraseTest() {
        DSLMatchQuery dslMatchQuery = new DSLMatchQuery("field", "a long phrase").setType(DSLMatchQuery.Type.MATCH_PHRASE);
        assertEquals("{\"match\":{\"field\":{\"query\":\"a long phrase\",\"type\":\"phrase\"}}}", dslMatchQuery.getQueryAsJson().toString());
        dslMatchQuery.setMaxExpansion((long)53);
        assertEquals("{\"match\":{\"field\":{\"query\":\"a long phrase\",\"type\":\"phrase\"}}}", dslMatchQuery.getQueryAsJson().toString());
        dslMatchQuery.setSlop(42);
        assertEquals("{\"match\":{\"field\":{\"query\":\"a long phrase\",\"type\":\"phrase\",\"slop\":42}}}", dslMatchQuery.getQueryAsJson().toString());
    }

    @Test
    public void matchPhrasePrefix() {
        DSLMatchQuery dslMatchQuery = new DSLMatchQuery("field", "a long phrase").setType(DSLMatchQuery.Type.MATCH_PHRASE_PREFIX);
        assertEquals("{\"match\":{\"field\":{\"query\":\"a long phrase\",\"type\":\"phrase_prefix\"}}}", dslMatchQuery.getQueryAsJson().toString());
        dslMatchQuery.setMaxExpansion((long)53);
        assertEquals("{\"match\":{\"field\":{\"query\":\"a long phrase\",\"type\":\"phrase_prefix\",\"max_expansions\":53}}}", dslMatchQuery.getQueryAsJson().toString());
        dslMatchQuery.setSlop(42);
        assertEquals("{\"match\":{\"field\":{\"query\":\"a long phrase\",\"type\":\"phrase_prefix\",\"max_expansions\":53,\"slop\":42}}}", dslMatchQuery.getQueryAsJson().toString());
    }

    @Test
    public void operatorTest() {
        DSLMatchQuery dslMatchQuery = new DSLMatchQuery("field", "value").setOperator(DSLMatchQuery.Operator.AND);
        assertEquals("{\"match\":{\"field\":{\"query\":\"value\",\"operator\":\"and\"}}}", dslMatchQuery.getQueryAsJson().toString());
    }

    @Test
    public void cutOffFrequencyTest() {
        DSLMatchQuery dslMatchQuery = new DSLMatchQuery("field", "value").setCutoff_Frequency(0.00001);
        assertEquals("{\"match\":{\"field\":{\"query\":\"value\",\"cutoff_frequency\":1.0E-5}}}", dslMatchQuery.getQueryAsJson().toString());
    }

    @Test
    public void minimumShouldMatch() {
        DSLMatchQuery dslMatchQuery = new DSLMatchQuery("field", "value").setMinimumShouldMatch("75%");
        assertEquals("{\"match\":{\"field\":{\"query\":\"value\",\"minimum_should_match\":\"75%\"}}}", dslMatchQuery.getQueryAsJson().toString());
    }

    @Test
    public void zeroTermsQuery() {
        DSLMatchQuery dslMatchQuery = new DSLMatchQuery("field", "value").setZroTermsQuery(DSLMatchQuery.Zero_Terms_Query.ALL);
        assertEquals("{\"match\":{\"field\":{\"query\":\"value\",\"zero_terms_query\":\"all\"}}}", dslMatchQuery.getQueryAsJson().toString());
    }
}
