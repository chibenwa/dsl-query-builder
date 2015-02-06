package tellier.es.dsl.query.builder.query;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import tellier.es.dsl.query.builder.Utilities.DSLMinimumShouldMatch;
import tellier.es.dsl.query.builder.Utilities.MatchUtilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Multi Match query.
 *
 * See : http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-multi-match-query.html
 */
public class DSLMultiMatchQuery implements DSLQuery{
    private static final String USE_DIS_MAX = "use_dis_max";
    private static final String MULTI_MATCH = "multi_match";
    private static final String FIELDS = "fields";
    private static final String TYPE = "type";

    private String query;
    private List<String> fields;
    private boolean use_dis_max;
    private DSLMultiMatchQuery.Type type;
    private MatchUtilities matchUtilities;

    public enum Type {
        BEST_FIELDS("best_fields"),
        MOST_FIELDS("most_fields"),
        CROSS_FIELDS("cross_fields"),
        PHRASE("phrase"),
        PHRASE_PREFIX("phrase_prefix");
        
        private String tag;
        
        Type(String tag) {
            this.tag = tag;
        }
        
        public String getTag() {
            return tag;
        }
    }

    public DSLMultiMatchQuery(String query) {
        this.matchUtilities = new MatchUtilities();
        this.query = query;
        this.fields = new ArrayList<String>();
        this.type = Type.BEST_FIELDS;
        this.use_dis_max = true;
    }

    public DSLMultiMatchQuery setUseDisMax(boolean use_dis_max) {
        this.use_dis_max = use_dis_max;
        return this;
    }

    public DSLMultiMatchQuery setType(Type type) {
        this.type = type;
        return this;
    }

    public DSLMultiMatchQuery setTieBreaker(Double tie_breaker) {
        matchUtilities.setTieBraker(tie_breaker);
        return this;
    }

    public DSLMultiMatchQuery addField(String field) {
        fields.add(field);
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject multiMatchObject = new JsonObject();
        multiMatchObject.add(QUERY, new JsonPrimitive(query));
        JsonArray jsonArrayFields = new JsonArray();
        for(String field : fields) {
            jsonArrayFields.add(new JsonPrimitive(field));
        }
        multiMatchObject.add(FIELDS, jsonArrayFields);
        if(type != Type.BEST_FIELDS) {
            multiMatchObject.add(TYPE, new JsonPrimitive(type.getTag()));
        }
        if(!use_dis_max) {
            multiMatchObject.add(USE_DIS_MAX, new JsonPrimitive(false));
        }
        matchUtilities.applyMatchUtilitiesOnJson(multiMatchObject);
        JsonObject result = new JsonObject();
        result.add(MULTI_MATCH, multiMatchObject);
        return result;
    }

    // Setters for common utilities
    public DSLMultiMatchQuery setAnalyser(String analyser) {
        matchUtilities.setAnalyser(analyser);
        return this;
    }

    public DSLMultiMatchQuery setMaxExpansion(Long max_expansions) {
        matchUtilities.setMax_expansions(max_expansions);
        return this;
    }

    public DSLMultiMatchQuery setCutoff_Frequency(Double cutoff_frequency) {
        matchUtilities.setCutoff_frequency(cutoff_frequency);
        return this;
    }

    public DSLMultiMatchQuery setOperator(MatchUtilities.Operator operator) {
        matchUtilities.setOperator(operator);
        return this;
    }

    public DSLMultiMatchQuery setMinimumShouldMatch(String minimumShouldMatch) {
        matchUtilities.setMinimumShouldMatch(minimumShouldMatch);
        return this;
    }

    public DSLMultiMatchQuery setZeroTermsQuery(MatchUtilities.Zero_Terms_Query zero_terms_query) {
        matchUtilities.setZero_terms_query(zero_terms_query);
        return this;
    }

    public DSLMultiMatchQuery setFuzziness(Integer fuzziness) {
        matchUtilities.setFuzziness(fuzziness);
        return this;
    }

    public DSLMultiMatchQuery setMinimumShouldMatch(DSLMinimumShouldMatch minimumShouldMatch) {
        matchUtilities.setMinimumShouldMatch(minimumShouldMatch);
        return this;
    }
}
