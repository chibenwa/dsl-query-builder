package tellier.es.dsl.query.builder.query;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import tellier.es.dsl.query.builder.filter.DSLFilter;

/**
 * Elastic search constant score query
 *
 * See details here : http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-constant-score-query.html
 */
public class DSLConstantScoreQuery implements DSLQuery {
    private DSLFilter filter;
    private DSLQuery query;
    private double boost;

    public final String BOOST = "boost";
    public final String CONSTANT_SCORE = "constant_score";

    public DSLConstantScoreQuery(DSLQuery query, double boost) {
        this.query = query;
        this.boost = boost;
    }

    public DSLConstantScoreQuery(DSLFilter filter, double boost) {
        this.filter = filter;
        this.boost = boost;
    }

    public JsonObject getQueryAsJson() {
        JsonObject constantScoreBody = new JsonObject();
        if(filter != null) {
            constantScoreBody.add(DSLFilter.FILTER, filter.getQueryAsJson());
        } else {
            constantScoreBody.add(QUERY, query.getQueryAsJson());
        }
        constantScoreBody.add(BOOST,  new JsonPrimitive(boost));
        JsonObject result = new JsonObject();
        result.add(CONSTANT_SCORE, constantScoreBody );
        return result;
    }
}
