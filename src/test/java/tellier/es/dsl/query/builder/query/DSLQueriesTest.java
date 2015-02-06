package tellier.es.dsl.query.builder.query;

import org.junit.Test;
import tellier.es.dsl.query.builder.Utilities.DSLDoc;
import tellier.es.dsl.query.builder.Utilities.DSLMinimumShouldMatch;
import tellier.es.dsl.query.builder.Utilities.shape.DSLEnvelope;
import tellier.es.dsl.query.builder.Utilities.shape.DSLPoint;
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
        assertEquals("{\"constant_score\":{\"filter\":{\"exists\":{\"field\":\"text\"}},\"boost\":0.8}}", dslQueryConstant.getQueryAsJson().toString());
    }

    @Test
    public void boostingQueriesTest() {
        DSLQuery positiveQuery = new DSLMatchAllQuery();
        DSLQuery negativeQuery = new DSLMatchQuery("user", "kimchi");
        DSLBoostingQuery dslBoostingQuery = new DSLBoostingQuery(positiveQuery, negativeQuery, 0.2);
        assertEquals("{\"boosting\":{\"positive\":{\"match_all\":{}},\"negative\":{\"match\":{\"user\":\"kimchi\"}},\"negative_boost\":0.2}}", dslBoostingQuery.getQueryAsJson().toString());
        dslBoostingQuery.setBoost(0.3);
        assertEquals("{\"boosting\":{\"positive\":{\"match_all\":{}},\"negative\":{\"match\":{\"user\":\"kimchi\"}},\"negative_boost\":0.2,\"boost\":0.3}}", dslBoostingQuery.getQueryAsJson().toString());
    }

    @Test
    public void commonQueryTest() {
        DSLQuery dslQuery = new DSLCommonQuery("to be or not to be")
                .setMinimumShouldMatch(
                        new DSLMinimumShouldMatch("2", "3")
                )
                .setHighFrequencyOperator(DSLCommonQuery.Freq_operator.AND)
                .setAnalyzer("my_analyser");
        assertEquals("{\"common\":{\"body\":{\"query\":\"to be or not to be\",\"minimum_should_match\":{\"low_freq\":\"2\",\"high_freq\":\"3\"},\"analyzer\":\"my_analyser\",\"high_freq_operator\":\"and\"}}}", dslQuery.getQueryAsJson().toString());
    }

    @Test
    public void disMaxTest() {
        DSLQuery query = new DSLDisMaxQuery().addQuery(new DSLMatchAllQuery()).addQuery(new DSLMatchQuery("toto", "tata")).setTieBreaker(0.3).setBoost(0.2);
        assertEquals("{\"dis_max\":{\"boost\":0.2,\"tie_breaker\":0.3,\"queries\":[{\"match_all\":{}},{\"match\":{\"toto\":\"tata\"}}]}}", query.getQueryAsJson().toString());
    }

    @Test
    public void fltTest() {
        DSLQuery query = new DSLFuzzyLikeThisQuery("This is a text")
                .addField("Summary")
                .addField("description")
                .setBoost(0.2)
                .setAnalyser("analyser")
                .setIgnore_tf(true)
                .setPrefixLength(2)
                .setFuzziness(2);
        assertEquals("{\"flt\":{\"fields\":[\"Summary\",\"description\"],\"like_text\":\"This is a text\",\"ignore_tf\":true,\"prefix_length\":2,\"analyzer\":\"analyser\",\"fuzziness\":2,\"boost\":0.2}}", query.getQueryAsJson().toString());
    }

    @Test
    public void flt_fieldTest() {
        DSLQuery query = new DSLFuzzyLikeThisFieldQuery("I like icecreams", "ad")
                .setBoost(0.2)
                .setAnalyser("analyser")
                .setIgnore_tf(true)
                .setPrefixLength(2)
                .setFuzziness(2);
        assertEquals("{\"flt_field\":{\"ad\":{\"like_text\":\"I like icecreams\",\"ignore_tf\":true,\"prefix_length\":2,\"analyzer\":\"analyser\",\"fuzziness\":2,\"boost\":0.2}}}", query.getQueryAsJson().toString());
    }

    @Test
    public void fuzzyTest() {
        DSLQuery query = new DSLFuzzyQuery("user", "ki").setFuzziness(2).setBoost(1.0).setPrefix_length(0).setMax_expansions(100);
        assertEquals("{\"fuzzy\":{\"user\":{\"value\":\"ki\",\"prefix_length\":0,\"fuzziness\":2,\"boost\":1.0,\"max_expansions\":100}}}", query.getQueryAsJson().toString());
    }

    @Test
    public void geoShapeTest() {
        DSLGeoShapeQuery query = new DSLGeoShapeQuery("location", new DSLEnvelope().addCoordinate(new DSLPoint(13.0,53.0))
                .addCoordinate(new DSLPoint(14.0, 52.0)));
        assertEquals("{\"geo_shape\":{\"location\":{\"shape\":{\"type\":\"envelope\",\"coordinates\":[[13.0,53.0],[14.0,52.0]]}}}}", query.getQueryAsJson().toString());
    }

    @Test
    public void hasChildTest() {
        DSLHasChildQuery query = new DSLHasChildQuery("clients", new DSLMatchQuery("name", "benwa"));
        assertEquals("{\"has_child\":{\"type\":\"clients\",\"query\":{\"match\":{\"name\":\"benwa\"}}}}", query.getQueryAsJson().toString());
    }

    @Test
    public void hasChildWithScoreModeTest() {
        DSLHasChildQuery query = new DSLHasChildQuery("clients", new DSLMatchQuery("name", "benwa")).setScoreMode(DSLHasChildQuery.ScoreMode.AVG);
        assertEquals("{\"has_child\":{\"type\":\"clients\",\"score_mode\":\"avg\",\"query\":{\"match\":{\"name\":\"benwa\"}}}}", query.getQueryAsJson().toString());
    }

    @Test
    public void hasParentTest() {
        DSLHasParentQuery query = new DSLHasParentQuery("clients", new DSLMatchQuery("field", "toto"));
        assertEquals("{\"has_parent\":{\"parent_type\":\"clients\",\"query\":{\"match\":{\"field\":\"toto\"}}}}", query.getQueryAsJson().toString());
    }

    @Test
    public void hasParentWithScoreModeTest() {
        DSLHasParentQuery query = new DSLHasParentQuery("clients", new DSLMatchQuery("field", "toto")).setScoreMode(DSLHasParentQuery.ScoreMode.SCORE);
        assertEquals("{\"has_parent\":{\"parent_type\":\"clients\",\"query\":{\"match\":{\"field\":\"toto\"}},\"score_mode\":\"score\"}}", query.getQueryAsJson().toString());
    }

    @Test
    public void IdsTest() {
        DSLIdsQuery query = new DSLIdsQuery().addType("my_type").addValue("1").addValue("4").addValue("100");
        assertEquals("{\"ids\":{\"type\":\"my_type\",\"values\":[\"1\",\"4\",\"100\"]}}", query.getQueryAsJson().toString());
    }

    @Test
    public void IndicesTest() {
        DSLIndicesQuery query = new DSLIndicesQuery(new DSLMatchAllQuery()).addIndice("indice1").addIndice("indice2");
        assertEquals("{\"indices\":{\"indices\":[\"indice1\",\"indice2\"],\"query\":{\"match_all\":{}}}}", query.getQueryAsJson().toString());
    }

    @Test
    public void IndicesWithNoMatchTest() {
        DSLIndicesQuery query = new DSLIndicesQuery(new DSLMatchAllQuery()).addIndice("indice1").addIndice("indice2").setNoMatchQuery(new DSLMatchQuery("tag", "kow"));
        assertEquals("{\"indices\":{\"indices\":[\"indice1\",\"indice2\"],\"query\":{\"match_all\":{}},\"no_match_query\":{\"match\":{\"tag\":\"kow\"}}}}", query.getQueryAsJson().toString());
    }

    @Test
    public void moreLikeThisQueryTest() {
        DSLMoreLikeThisQuery query = new DSLMoreLikeThisQuery().addDoc(new DSLDoc("index", "type", "id")).addDoc(new DSLDoc("index2", "type2", "id2"))
                .addField("field").addField("toto").setMaxQueryTerms(10).setBoostTerms(11).setExclude(true).setLikeText("This text should be like this one")
                .addStopWord("a").addStopWord("the").addStopWord("is").setMaxDocFreq(100).setMinDocFreq(2).setMinTermFreq(4).setMinWordLength(3).setMaxWordLength(200)
                .setPercentTermsToMatch(0.25).setAnalyser("my_analyser").setBoostTerms(5).setBoost(0.2);
        assertEquals("{\"mlt\":{\"fields\":[\"field\",\"toto\"],\"docs\":[{\"_index\":\"index\",\"_type\":\"type\",\"_id\":\"id\"},{\"_index\":\"index2\",\"_type\":\"type2\",\"_id\":\"id2\"}],\"exclude\":true,\"like_text\":\"This text should be like this one\",\"percent_terms_to_match\":0.25,\"min_term_freq\":4,\"max_query_terms\":10,\"stop_words\":[\"a\",\"the\",\"is\"],\"min_doc_freq\":2,\"max_doc_freq\":100,\"min_word_length\":3,\"max_word_length\":200,\"boost_terms\":5,\"analyzer\":\"my_analyser\",\"boost\":0.2}}", query.getQueryAsJson().toString());
    }

    @Test
    public void moreLikeThisFieldTest() {
        DSLMoreLikeThisFieldQuery query = new DSLMoreLikeThisFieldQuery("user.name").setMaxQueryTerms(10).setBoostTerms(11)
                .setLikeText("This text should be like this one").addStopWord("a").addStopWord("the").addStopWord("is")
                .setMaxDocFreq(100).setMinDocFreq(2).setMinTermFreq(4).setMinWordLength(3).setMaxWordLength(200)
                .setPercentTermsToMatch(0.25).setAnalyser("my_analyser").setBoostTerms(5).setBoost(0.2);
        assertEquals("{\"mlt_field\":{\"user.name\":{\"like_text\":\"This text should be like this one\",\"percent_terms_to_match\":0.25,\"min_term_freq\":4,\"max_query_terms\":10,\"stop_words\":[\"a\",\"the\",\"is\"],\"min_doc_freq\":2,\"max_doc_freq\":100,\"min_word_length\":3,\"max_word_length\":200,\"boost_terms\":5,\"analyzer\":\"my_analyser\",\"boost\":0.2}}}", query.getQueryAsJson().toString());
    }

    @Test
    public void prefixTest() {
        DSLPrefixQuery query = new DSLPrefixQuery("user", "ben");
        assertEquals("{\"prefix\":{\"user\":\"ben\"}}", query.getQueryAsJson().toString());
    }

    @Test
    public void prefixWithBoostTest() {
        DSLPrefixQuery query = new DSLPrefixQuery("user", "ben").setBoost(0.45);
        assertEquals("{\"prefix\":{\"user\":{\"prefix\":\"ben\",\"boost\":0.45}}}", query.getQueryAsJson().toString());
    }

    @Test
    public void rangeTest() {
        DSLRangeQuery query = new DSLRangeQuery("born").gte("2012-01-01").lte("now").setTimeZone("+1:00");
        assertEquals("{\"range\":{\"born\":{\"gte\":\"2012-01-01\",\"lte\":\"now\",\"time_zone\":\"+1:00\"}}}", query.getQueryAsJson().toString());
        query = new DSLRangeQuery("age").gte(10).lte(20).setBoost(2.0);
        assertEquals("{\"range\":{\"age\":{\"gte\":10,\"lte\":20,\"boost\":2.0}}}", query.getQueryAsJson().toString());
    }

    @Test
    public void regexpTest() {
        DSLRegexpQuery query = new DSLRegexpQuery("name.first", "s.*y");
        assertEquals("{\"regexp\":{\"name.first\":\"s.*y\"}}", query.getQueryAsJson().toString());
        query.setBoost(1.2);
        assertEquals("{\"regexp\":{\"name.first\":{\"value\":\"s.*y\",\"boost\":1.2}}}", query.getQueryAsJson().toString());
        query = new DSLRegexpQuery("name.first", "s.*y").addFlag(DSLRegexpQuery.Flag.INTERSECTION).addFlag(DSLRegexpQuery.Flag.COMPLEMENT)
                .addFlag(DSLRegexpQuery.Flag.EMPTY);
        assertEquals("{\"regexp\":{\"name.first\":{\"value\":\"s.*y\",\"flags\":\"INTERSECTION|COMPLEMENT|EMPTY\"}}}", query.getQueryAsJson().toString());
    }

    @Test
    public void spanTermTest() {
        DSLSpanTermQuery query = new DSLSpanTermQuery("user", "kimchy");
        assertEquals("{\"span-term\":{\"user\":\"kimchy\"}}", query.getQueryAsJson().toString());
        query.setBoost(2.0);
        assertEquals("{\"span-term\":{\"user\":{\"value\":\"kimchy\",\"boost\":2.0}}}", query.getQueryAsJson().toString());
    }

    @Test
    public void spanFirstTest() {
        DSLSpanFirstQuery query = new DSLSpanFirstQuery(new DSLSpanTermQuery("user", "kimchy"), 3);
        assertEquals("{\"span_first\":{\"match\":{\"span-term\":{\"user\":\"kimchy\"}},\"end\":3}}", query.getQueryAsJson().toString());
    }

    @Test
    public void spanNearTest() {
        DSLSpanNearQuery query = new DSLSpanNearQuery().addClause(new DSLSpanTermQuery("field", "value1"))
                .addClause(new DSLSpanTermQuery("field", "value2")).addClause(new DSLSpanTermQuery("field", "value3"))
                .setSlop(12).setInOrder(false).setCollectPayloads(false);
        assertEquals("{\"span_near\":{\"clauses\":[{\"span-term\":{\"field\":\"value1\"}},{\"span-term\":{\"field\":\"value2\"}},{\"span-term\":{\"field\":\"value3\"}}],\"slop\":12,\"in_order\":false,\"collect_payloads\":false}}", query.getQueryAsJson().toString());
    }

    @Test
    public void spanNotTest() {
        DSLSpanNotQuery query = new DSLSpanNotQuery(new DSLSpanTermQuery("field1","hoya"),
                new DSLSpanNearQuery().addClause(new DSLSpanTermQuery("field1", "la"))
                        .addClause(new DSLSpanTermQuery("field1", "hoya")).setSlop(0).setInOrder(true)
        );
        assertEquals("{\"span_not\":{\"include\":{\"span-term\":{\"field1\":\"hoya\"}},\"exclude\":{\"span_near\":{\"clauses\":[{\"span-term\":{\"field1\":\"la\"}},{\"span-term\":{\"field1\":\"hoya\"}}],\"slop\":0,\"in_order\":true}}}}", query.getQueryAsJson().toString());
    }

    @Test
    public void spanOrTest() {
        DSLSpanOrQuery query = new DSLSpanOrQuery().addClause(new DSLSpanTermQuery("field", "value1"))
                .addClause(new DSLSpanTermQuery("field", "value2")).addClause(new DSLSpanTermQuery("field", "value3"));
        assertEquals("{\"span_or\":{\"clauses\":[{\"span-term\":{\"field\":\"value1\"}},{\"span-term\":{\"field\":\"value2\"}},{\"span-term\":{\"field\":\"value3\"}}]}}", query.getQueryAsJson().toString());
    }

    @Test
    public void spanMultiTermTest() {
        DSLSpanMultiTermQuery query = new DSLSpanMultiTermQuery(new DSLPrefixQuery("user", "ki").setBoost(1.08));
        assertEquals("{\"span_multi\":{\"match\":{\"prefix\":{\"user\":{\"prefix\":\"ki\",\"boost\":1.08}}}}}", query.getQueryAsJson().toString());
    }

    @Test
    public void termTest() {
        DSLTermQuery query = new DSLTermQuery("user", "kimchy");
        assertEquals("{\"term\":{\"user\":\"kimchy\"}}", query.getQueryAsJson().toString());
        query.setBoost(2.0);
        assertEquals("{\"term\":{\"user\":{\"term\":\"kimchy\",\"boost\":2.0}}}", query.getQueryAsJson().toString());
    }

    @Test
    public void termsTest() {
        DSLTermsQuery query = new DSLTermsQuery("tags", 1).addTerm("blue").addTerm("pill");
        assertEquals("{\"terms\":{\"tags\":[\"blue\",\"pill\"],\"minimum_should_match\":1}}", query.getQueryAsJson().toString());
    }

    @Test
    public void topChildrenTest() {
        DSLTopChildrenQuery query = new DSLTopChildrenQuery("blog_tag", new DSLTermQuery("tag", "something"))
                .setScore(DSLTopChildrenQuery.Score.MAX).setFactor(5).setIncrementalFactor(2);
        assertEquals("{\"top_children\":{\"type\":\"blog_tag\",\"query\":{\"term\":{\"tag\":\"something\"}},\"score\":\"max\",\"factor\":5,\"incremental_factor\":2}}", query.getQueryAsJson().toString());
        query.setScope("my_scope");
        assertEquals("{\"top_children\":{\"type\":\"blog_tag\",\"query\":{\"term\":{\"tag\":\"something\"}},\"_scope\":\"my_scope\",\"score\":\"max\",\"factor\":5,\"incremental_factor\":2}}", query.getQueryAsJson().toString());
    }

    @Test
    public void wildcardTest() {
        DSLWildcardQuery query = new DSLWildcardQuery("user", "ki*y");
        assertEquals("{\"wildcard\":{\"user\":\"ki*y\"}}", query.getQueryAsJson().toString());
        query.setBoost(2.0);
        assertEquals("{\"wildcard\":{\"user\":{\"wildcard\":\"ki*y\",\"boost\":2.0}}}", query.getQueryAsJson().toString());
    }

    @Test
    public void templateTest() {
        DSLTemplateQuery query = new DSLTemplateQuery().setSubQuery(new DSLMatchQuery("text", "{query_string}")).addParam("query_string", "all about search");
        assertEquals("{\"template\":{\"params\":{\"query_string\":\"all about search\"},\"query\":{\"match\":{\"text\":\"{query_string}\"}}}}", query.getQueryAsJson().toString());
        query = new DSLTemplateQuery().setFile("my_template").addParam("query_string", "all about search");
        assertEquals("{\"template\":{\"params\":{\"query_string\":\"all about search\"},\"file\":\"my_template\"}}", query.getQueryAsJson().toString());
        query = new DSLTemplateQuery().setId("my_template").addParam("query_string", "all about search");
        assertEquals("{\"template\":{\"params\":{\"query_string\":\"all about search\"},\"id\":\"my_template\"}}", query.getQueryAsJson().toString());
    }

}
