package tellier.es.dsl.query.builder.Utilities;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Code factorization for DSLFuzzyLikeThisQuery and DSLFuzzyLikeThisFieldQuery
 */
public class FuzzyQueriesUtilities {
    public final String LIKE_TEXT = "like_text";
    public final String IGNORE_TF = "ignore_tf";
    public final String MAX_QUERY_TERMS = "max_query_terms";
    public final String PREFIX_LENGTH = "prefix_length";

    private String like_text;
    private Boolean ignore_tf;
    private Integer max_query_terms;
    private Integer prefix_length;

    public void setLike_text(String like_text) {
        this.like_text = like_text;
    }

    public void setIgnore_tf(Boolean ignore_tf) {
        this.ignore_tf = ignore_tf;
    }

    public void setMax_query_terms(Integer max_query_terms) {
        this.max_query_terms = max_query_terms;
    }

    public void setPrefix_length(Integer prefix_length) {
        this.prefix_length = prefix_length;
    }

    public void applyMatchUtilitiesOnJson(JsonObject queryJson) {
        if(like_text != null) {
            queryJson.add(LIKE_TEXT, new JsonPrimitive(like_text));
        }
        if(ignore_tf != null) {
            queryJson.add(IGNORE_TF, new JsonPrimitive(ignore_tf));
        }
        if(max_query_terms != null) {
            queryJson.add(MAX_QUERY_TERMS, new JsonPrimitive(max_query_terms));
        }
        if(prefix_length != null) {
            queryJson.add(PREFIX_LENGTH, new JsonPrimitive(prefix_length));
        }
    }
}
