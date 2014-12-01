package tellier.es.dsl.query.builder.query;

public interface DSLSpecializedBoolQuery extends DSLQuery {
    public void addQuery(DSLQuery query);
}
