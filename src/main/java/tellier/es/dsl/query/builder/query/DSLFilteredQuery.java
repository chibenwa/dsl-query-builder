package tellier.es.dsl.query.builder.query;

import com.google.gson.JsonObject;
import tellier.es.dsl.query.builder.filter.DSLFilter;

/**
 * Created by benwa on 11/28/14.
 */
public class DSLFilteredQuery implements DSLQuery {
    public final String FILTER = "filter";
    public final String FILTERED = "filtered";

    private DSLFilter filter;
    private DSLQuery query;

    public DSLFilteredQuery(DSLQuery query, DSLFilter filter) {
        this.filter = filter;
        this.query = query;
    }

    public DSLFilter getFilter() {
        return filter;
    }

    public void setFilter(DSLFilter filter) {
        this.filter = filter;
    }

    public DSLQuery getQuery() {
        return query;
    }

    public void setQuery(DSLQuery query) {
        this.query = query;
    }

    public JsonObject getQueryAsJson() {
        if(filter == null) {
            if(query == null) {
                return null;
            }
            return query.getQueryAsJson();
        }
        if(query == null) {
            query = new DSLMatchAllQuery();
        }
        return combineQueryAndFilter();
    }



    private JsonObject combineQueryAndFilter() {
        JsonObject body = new JsonObject();
        body.add(QUERY, query.getQueryAsJson());
        body.add(FILTER, filter.getQueryAsJson());
        JsonObject filteredObject = new JsonObject();
        filteredObject.add(FILTERED, body);
        return filteredObject;
    }
}
