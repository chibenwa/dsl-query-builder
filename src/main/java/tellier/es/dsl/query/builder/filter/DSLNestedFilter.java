package tellier.es.dsl.query.builder.filter;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Created by benwa on 11/28/14.
 */
public class DSLNestedFilter implements DSLFilter {

    private String path;
    private DSLFilter subFilter;
    private boolean cache;

    public DSLNestedFilter(String path, DSLFilter subFilter) {
        this(path, subFilter, false);
    }

    public DSLNestedFilter(String path, DSLFilter subFilter, boolean cache) {
        this.path = path;
        this.subFilter = subFilter;
        this.cache = cache;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public DSLFilter getSubFilter() {
        return subFilter;
    }

    public void setSubFilter(DSLFilter subFilter) {
        this.subFilter = subFilter;
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public JsonObject getQueryAsJson() {
        JsonObject filterJson = new JsonObject();
        filterJson.add(PATH, new JsonPrimitive(path));
        filterJson.add(FILTER, subFilter.getQueryAsJson());
        JsonObject result = new JsonObject();
        result.add(NESTED, filterJson);
        return result;
    }
}
