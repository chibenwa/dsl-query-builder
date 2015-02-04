
package tellier.es.dsl.query.builder.query;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import tellier.es.dsl.query.builder.Utilities.DSLDoc;
import tellier.es.dsl.query.builder.Utilities.MatchUtilities;
import tellier.es.dsl.query.builder.Utilities.MoreLikeThisUtilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a More Like This Query
 */
public class DSLMoreLikeThisQuery implements DSLQuery {

    private static final String MORE_LIKE_THIS = "mlt";
    private static final String FIELDS = "fields";
    private static final String DOCS = "docs";
    private static final String IDS = "ids";
    private static final String INCLUDE = "include";
    private static final String EXCLUDE = "exclude";

    private MoreLikeThisUtilities moreLikeThisUtilities = new MoreLikeThisUtilities();
    private MatchUtilities matchUtilities = new MatchUtilities();

    private List<String> fields = new ArrayList<String>();
    private List<DSLDoc> docs = new ArrayList<DSLDoc>();
    private List<String> ids = new ArrayList<String>();
    private boolean include = false;
    private boolean exclude = false;

    public DSLMoreLikeThisQuery setInclude(boolean include) {
        this.include = include;
        return this;
    }

    public DSLMoreLikeThisQuery setExclude(boolean exclude) {
        this.exclude = exclude;
        return this;
    }

    public DSLMoreLikeThisQuery addField(String field) {
        fields.add(field);
        return this;
    }

    public DSLMoreLikeThisQuery addDoc(DSLDoc doc) {
        docs.add(doc);
        return this;
    }

    public DSLMoreLikeThisQuery addId(String id) {
        ids.add(id);
        return this;
    }

    public DSLMoreLikeThisQuery setAnalyser(String analyser) {
        matchUtilities.setAnalyser(analyser);
        return this;
    }

    public DSLMoreLikeThisQuery setBoost(Double boost) {
        matchUtilities.setBoost(boost);
        return this;
    }

    public JsonObject getQueryAsJson() {
        JsonObject result = new JsonObject();
        JsonObject moreLikeThisObject = new JsonObject();
        moreLikeThisObject.add(FIELDS, getFieldsArray());
        if(docs.size() > 0) {
            moreLikeThisObject.add(DOCS, getDocsArray());
        }
        if(ids.size() > 0) {
            moreLikeThisObject.add(IDS, getIdsArray());
        }
        if(include) {
            moreLikeThisObject.add(INCLUDE, new JsonPrimitive(true));
        }
        if(exclude) {
            moreLikeThisObject.add(EXCLUDE, new JsonPrimitive(true));
        }
        moreLikeThisObject = moreLikeThisUtilities.fillJsonObject(moreLikeThisObject);
        matchUtilities.applyMatchUtilitiesOnJson(moreLikeThisObject);
        result.add(MORE_LIKE_THIS, moreLikeThisObject);
        return result;
    }

    private JsonArray getIdsArray() {
        JsonArray idsArray = new JsonArray();
        for(String id : ids) {
            idsArray.add(new JsonPrimitive(id));
        }
        return idsArray;
    }

    private JsonArray getDocsArray() {
        JsonArray docsArray = new JsonArray();
        for(DSLDoc doc : docs) {
            docsArray.add(doc.getAsJson());
        }
        return docsArray;
    }

    private JsonArray getFieldsArray() {
        JsonArray fieldsArray = new JsonArray();
        for(String field : fields) {
            fieldsArray.add(new JsonPrimitive(field));
        }
        return fieldsArray;
    }

    public DSLMoreLikeThisQuery setLikeText(String likeText) {
        moreLikeThisUtilities.setLikeText(likeText);
        return this;
    }

    public DSLMoreLikeThisQuery setPercentTermsToMatch(Double percentTermsToMatch) {
        moreLikeThisUtilities.setPercentTermsToMatch(percentTermsToMatch);
        return this;
    }

    public DSLMoreLikeThisQuery setMinTermFreq(Integer minTermFreq) {
        moreLikeThisUtilities.setMinTermFreq(minTermFreq);
        return this;
    }

    public DSLMoreLikeThisQuery setMaxQueryTerms(Integer maxQueryTerms) {
        moreLikeThisUtilities.setMaxQueryTerms(maxQueryTerms);
        return this;
    }

    public DSLMoreLikeThisQuery setMinDocFreq(Integer minDocFreq) {
        moreLikeThisUtilities.setMinDocFreq(minDocFreq);
        return this;
    }

    public DSLMoreLikeThisQuery setMaxDocFreq(Integer maxDocFreq) {
        moreLikeThisUtilities.setMaxDocFreq(maxDocFreq);
        return this;
    }

    public DSLMoreLikeThisQuery setMinWordLength(Integer minWordLength) {
        moreLikeThisUtilities.setMinWordLength(minWordLength);
        return this;
    }

    public DSLMoreLikeThisQuery setMaxWordLength(Integer maxWordLength) {
        moreLikeThisUtilities.setMaxWordLength(maxWordLength);
        return this;
    }

    public DSLMoreLikeThisQuery setBoostTerms(Integer boostTerms) {
        moreLikeThisUtilities.setBoostTerms(boostTerms);
        return this;
    }

    public DSLMoreLikeThisQuery addStopWord(String stopWord) {
        moreLikeThisUtilities.addStopWord(stopWord);
        return this;
    }

}
