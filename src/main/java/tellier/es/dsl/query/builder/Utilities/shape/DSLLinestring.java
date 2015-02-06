package tellier.es.dsl.query.builder.Utilities.shape;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.List;

/**
 * Linestring shape
 */
public class DSLLinestring implements DSLShape {
    
    private final static String LINESTRING = "linestring";
    
    private List<DSLPoint> points;

    public DSLLinestring addPoint(DSLPoint point) {
        points.add(point);
        return this;
    }

    JsonArray getPointArray() {
        JsonArray result = new JsonArray();
        for(DSLPoint point : points) {
            result.add(point.getJsonArray());
        }
        return result;
    }

    public JsonElement getShapeAsJson() {
        JsonObject result = new JsonObject();
        result.add(TYPE, new JsonPrimitive(LINESTRING));
        result.add(COORDINATES, getPointArray());
        return result;
    }
    
}
