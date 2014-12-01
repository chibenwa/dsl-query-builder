package tellier.es.dsl.query.builder.query;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 *
 */
public class DSLNestedQuery implements DSLQuery {
    private String path;
    private Score_mode scoreMode;
    private DSLQuery subQuery;

    public enum Score_mode {
        AVG,
        SUM,
        MAX,
        NONE
    }

    public JsonObject getQueryAsJson() {
        JsonObject nestedContent = new JsonObject();
        nestedContent.add(PATH, new JsonPrimitive(path));
        switch (scoreMode) {
            case AVG:
                nestedContent.add(SCORE_MODE, new JsonPrimitive(AVG));
                break;
            case MAX:
                nestedContent.add(SCORE_MODE, new JsonPrimitive(MAX));
                break;
            case NONE:
                nestedContent.add(SCORE_MODE, new JsonPrimitive(NONE));
                break;
            case SUM:
                nestedContent.add(SCORE_MODE, new JsonPrimitive(SUM));
                break;
        }
        nestedContent.add(QUERY, subQuery.getQueryAsJson());
        JsonObject finalQuery = new JsonObject();
        finalQuery.add(NESTED, nestedContent);
        return finalQuery;
    }

    public DSLNestedQuery(String path, Score_mode scoreMode, DSLQuery subQuery) {
        this.path = path;
        this.scoreMode = scoreMode;
        this.subQuery = subQuery;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Score_mode getScoreMode() {
        return scoreMode;
    }

    public void setScoreMode(Score_mode scoreMode) {
        this.scoreMode = scoreMode;
    }

    public DSLQuery getSubQuery() {
        return subQuery;
    }

    public void setSubQuery(DSLQuery subQuery) {
        this.subQuery = subQuery;
    }
}
