package tellier.es.dsl.query.builder.query;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an indices query
 *
 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-indices-query.html
 */
public class DSLIndicesQuery implements DSLQuery {

    private final static String INDICES = "indices";
    private final static String QUERY = "query";
    private final static String NO_MATCH_QUERY = "no_match_query";

    private List<String> indices = new ArrayList<String>();
    private DSLQuery query;
    private DSLQuery noMatchQuery;

    public DSLIndicesQuery(DSLQuery query) {
        this.query = query;
    }

    public DSLIndicesQuery setNoMatchQuery(DSLQuery noMatchQuery) {
        this.noMatchQuery = noMatchQuery;
        return this;
    }

    public DSLIndicesQuery addIndice(String indice) {
        indices.add(indice);
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject result = new JsonObject();
        JsonObject indicesObject = new JsonObject();
        result.add(INDICES, indicesObject);
        JsonArray indicesArray = new JsonArray();
        for(String indice : indices) {
            indicesArray.add(new JsonPrimitive(indice));
        }
        indicesObject.add(INDICES, indicesArray);
        indicesObject.add(QUERY, query.getQueryAsJson());
        if(noMatchQuery != null) {
            indicesObject.add(NO_MATCH_QUERY, noMatchQuery.getQueryAsJson());
        }
        return result;
    }

}
