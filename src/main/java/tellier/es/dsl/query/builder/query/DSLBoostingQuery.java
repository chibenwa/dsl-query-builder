package tellier.es.dsl.query.builder.query;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Elastic search boosting query
 *
 * See details here : http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-boosting-query.html
 */
public class DSLBoostingQuery implements DSLQuery {
    public final String POSITIVE = "positive";
    public final String NEGATIVE = "negative";
    public final String NEGATIVE_BOOST = "negative_boost";
    public final String BOOST = "boost";
    public final String BOOSTING = "boosting";

    private DSLQuery positiveQuery;
    private DSLQuery negativeQuery;
    private double negativeBoost;
    private Double boost;

    public DSLBoostingQuery(DSLQuery positiveQuery, DSLQuery negativeQuery, double negativeBoost) {
        this.positiveQuery = positiveQuery;
        this.negativeQuery = negativeQuery;
        this.negativeBoost = negativeBoost;
    }

    public DSLBoostingQuery setBoost(Double boost) {
        this.boost = boost;
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject boostingBody = new JsonObject();
        boostingBody.add(POSITIVE, positiveQuery.getQueryAsJson());
        boostingBody.add(NEGATIVE, negativeQuery.getQueryAsJson());
        boostingBody.add(NEGATIVE_BOOST, new JsonPrimitive(negativeBoost));
        if(boost != null) {
            boostingBody.add(BOOST, new JsonPrimitive(boost));
        }
        JsonObject result = new JsonObject();
        result.add(BOOSTING, boostingBody);
        return result;
    }

}
