package tellier.es.dsl.query.builder.filter;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Exists filter implementation
 */
public class DSLExistFilter implements DSLFilter {

    private String field;
    private String nullValue;

    public DSLExistFilter(String field) {
        this.field = field;
        nullValue = null;
    }

    public DSLExistFilter setNullValue(String nullValue) {
        this.nullValue = nullValue;
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add(FIELD, new JsonPrimitive(field));
        if(nullValue != null) {
            jsonObject.add(NULL_VALUE, new JsonPrimitive(nullValue));
        }
        JsonObject result = new JsonObject();
        result.add(EXISTS, jsonObject);
        return result;
    }


}
