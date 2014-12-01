package tellier.es.dsl.query.builder.query;

public interface DSLSpecializedBoolQuery extends DSLQuery {
    public DSLSpecializedBoolQuery addQuery(DSLQuery query);
}
