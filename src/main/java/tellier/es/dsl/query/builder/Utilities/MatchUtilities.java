package tellier.es.dsl.query.builder.Utilities;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Code factorisation purpose
 */
public class MatchUtilities {

    public final String OPERATOR = "operator";
    public final String AND = "and";
    public final String MINIMUM_SHOULD_MATCH = "minimum_should_match";
    public final String ANALYZER = "analyzer";
    public final String CUT_OFF_FREQUENCY = "cutoff_frequency";
    public final String MAX_EXPANSIONS = "max_expansions";
    public final String ZERO_TERMS_QUERY = "zero_terms_query";
    public final String ALL = "all";
    public final String FUZZINESS = "fuzziness";

    private String analyser;
    private Double cutoff_frequency;
    private String minimumShouldMatch;
    private Operator operator;
    private Integer fuzziness;
    private Long max_expansions;
    private Zero_Terms_Query zero_terms_query;


    public MatchUtilities() {
        this.operator = Operator.OR;
        this.zero_terms_query = Zero_Terms_Query.NONE;
    }

    public enum Zero_Terms_Query {
        NONE,
        ALL
    }

    public enum Operator {
        AND,
        OR
    }

    public String getAnalyser() {
        return analyser;
    }

    public void setAnalyser(String analyser) {
        this.analyser = analyser;
    }

    public Double getCutoff_frequency() {
        return cutoff_frequency;
    }

    public void setCutoff_frequency(Double cutoff_frequency) {
        this.cutoff_frequency = cutoff_frequency;
    }

    public String getMinimumShouldMatch() {
        return minimumShouldMatch;
    }

    public void setMinimumShouldMatch(String minimumShouldMatch) {
        this.minimumShouldMatch = minimumShouldMatch;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Integer getFuzziness() {
        return fuzziness;
    }

    public void setFuzziness(Integer fuzziness) {
        this.fuzziness = fuzziness;
    }

    public Long getMax_expansions() {
        return max_expansions;
    }

    public void setMax_expansions(Long max_expansions) {
        this.max_expansions = max_expansions;
    }

    public Zero_Terms_Query getZero_terms_query() {
        return zero_terms_query;
    }

    public void setZero_terms_query(Zero_Terms_Query zero_terms_query) {
        this.zero_terms_query = zero_terms_query;
    }

    public boolean isEmpty() {
        return (analyser == null && max_expansions == null && cutoff_frequency == null && operator == Operator.OR && minimumShouldMatch == null && zero_terms_query == Zero_Terms_Query.NONE && fuzziness == null);
    }

    public void applyMatchUtilitiesOnJson(JsonObject queryJson) {
        if(operator == Operator.AND) {
            queryJson.add(OPERATOR, new JsonPrimitive(AND) );
        }
        if(minimumShouldMatch != null) {
            queryJson.add(MINIMUM_SHOULD_MATCH, new JsonPrimitive(minimumShouldMatch) );
        }
        if(analyser != null) {
            queryJson.add(ANALYZER, new JsonPrimitive(analyser));
        }
        if(cutoff_frequency != null) {
            queryJson.add(CUT_OFF_FREQUENCY, new JsonPrimitive(cutoff_frequency));
        }
        if(zero_terms_query == Zero_Terms_Query.ALL) {
            queryJson.add(ZERO_TERMS_QUERY, new JsonPrimitive(ALL));
        }
        if(fuzziness != null) {
            queryJson.add(FUZZINESS, new JsonPrimitive(fuzziness));
        }
        if(max_expansions != null) {
            queryJson.add(MAX_EXPANSIONS, new JsonPrimitive(max_expansions));
        }
    }
}
