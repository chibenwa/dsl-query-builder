package tellier.es.dsl.query.builder.query;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import tellier.es.dsl.query.builder.Utilities.DSLMinimumShouldMatch;
import tellier.es.dsl.query.builder.Utilities.MatchUtilities;

/**
 * A DSL common query
 *
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-common-terms-query.html for details.
 */
public class DSLCommonQuery implements DSLQuery {

    public final String COMMON = "common";
    public final String BODY = "body";
    public final String AND = "and";
    public final String LOW_FREQ_OPERATOR = "low_freq_operator";
    public final String HIGH_FREQ_OPERATOR = "high_freq_operator";

    private String query;
    private MatchUtilities matchUtilities;
    private Freq_operator low_freq_operator;
    private Freq_operator high_freq_operator;


    public DSLCommonQuery(String query) {
        this.query = query;
        this.matchUtilities = new MatchUtilities();
        this.low_freq_operator = Freq_operator.OR;
        this.high_freq_operator = Freq_operator.OR;
    }

    public DSLCommonQuery setCutoffFrequency(Double d) {
        matchUtilities.setCutoff_frequency(d);
        return this;
    }

    enum Freq_operator {
        AND,
        OR
    }

    public DSLCommonQuery setLowFrequencyOperator(Freq_operator operator) {
        this.low_freq_operator = operator;
        return this;
    }

    public DSLCommonQuery setHighFrequencyOperator(Freq_operator operator) {
        this.high_freq_operator = operator;
        return this;
    }

    public DSLCommonQuery setMinimumShouldMatch(String minimumShouldMatch) {
        this.matchUtilities.setMinimumShouldMatch(minimumShouldMatch);
        return this;
    }

    public DSLCommonQuery setMinimumShouldMatch(DSLMinimumShouldMatch minimumShouldMatch) {
        this.matchUtilities.setMinimumShouldMatch(minimumShouldMatch);
        return this;
    }

    public DSLCommonQuery setAnalyzer(String analyzer) {
        this.matchUtilities.setAnalyser(analyzer);
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject jsonContent = new JsonObject();
        jsonContent.add(QUERY, new JsonPrimitive(query));
        matchUtilities.applyMatchUtilitiesOnJson(jsonContent);
        if(low_freq_operator == Freq_operator.AND) {
            jsonContent.add(LOW_FREQ_OPERATOR, new JsonPrimitive(AND));
        }
        if(high_freq_operator == Freq_operator.AND) {
            jsonContent.add(HIGH_FREQ_OPERATOR, new JsonPrimitive(AND));
        }
        JsonObject body = new JsonObject();
        body.add(BODY, jsonContent);
        JsonObject result = new JsonObject();
        result.add(COMMON, body);
        return result;
    }

    // TODO BOOST support, disable_coord support
}
