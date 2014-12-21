package tellier.es.dsl.query.builder.query;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import tellier.es.dsl.query.builder.Utilities.MatchUtilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an ElasticSearch Dis Max Query.
 *
 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-dis-max-query.html
 */
public class DSLDisMaxQuery implements DSLQuery {

    public final String DIS_MAX = "dis_max";
    public final String QUERIES = "queries";

    public MatchUtilities matchUtilities;
    public List<DSLQuery> queryList;

    public DSLDisMaxQuery() {
        matchUtilities = new MatchUtilities();
        queryList = new ArrayList<DSLQuery>();
    }

    public DSLDisMaxQuery setBoost(Double boost) {
        matchUtilities.setBoost(boost);
        return this;
    }

    public DSLDisMaxQuery setTieBreaker(Double tieBreaker) {
        matchUtilities.setTieBraker(tieBreaker);
        return this;
    }

    public DSLDisMaxQuery addQuery(DSLQuery query) {
        queryList.add(query);
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonArray queryArray = new JsonArray();
        for(DSLQuery query : queryList) {
            queryArray.add(query.getQueryAsJson());
        }
        JsonObject disMaxJson = new JsonObject();
        matchUtilities.applyMatchUtilitiesOnJson(disMaxJson);
        disMaxJson.add(QUERIES, queryArray);
        JsonObject result = new JsonObject();
        result.add(DIS_MAX, disMaxJson);
        return result;
    }

}
