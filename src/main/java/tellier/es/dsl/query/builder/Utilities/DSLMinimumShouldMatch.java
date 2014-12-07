package tellier.es.dsl.query.builder.Utilities;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Minimum should match parameters for match, multi match and common queries
 */
public class DSLMinimumShouldMatch {
    private String lowFreq;
    private String highFreq;

    public final String LOW_FREQ = "low_freq";
    public final String HIGH_FREQ = "high_freq";

    public DSLMinimumShouldMatch(String lowFreq, String highFreq) {
        this.lowFreq = lowFreq;
        this.highFreq = highFreq;
    }

    public JsonObject getParameterAsJson() {
        JsonObject body = new JsonObject();
        body.add(LOW_FREQ, new JsonPrimitive(lowFreq));
        body.add(HIGH_FREQ, new JsonPrimitive(highFreq));
        return body;
    }
}
