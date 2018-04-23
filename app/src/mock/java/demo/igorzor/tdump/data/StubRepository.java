package demo.igorzor.tdump.data;

import java.util.ArrayList;

import demo.igorzor.tdump.data.API.QuoteService;
import demo.igorzor.tdump.data.model.Quote;
import demo.igorzor.tdump.data.model.remote.quotelistresponse.QuoteListResponse;
import demo.igorzor.tdump.data.model.remote.taglistresponse.TagListResponse;
import io.reactivex.Single;

public class StubRepository implements DataSource {

    private static final String MOCK_TAG = "MockFlavourTag";
    private static final String MOCK_QUOTE= "MockFlavourQuote, lorem ipsum, dolor sit amet";
    private static final String MOCK_URL="https://twitter.com/";
    private static final String MOCK_DATE = "2016-07-30T19:55:26";


    @Override
    public QuoteService getQuoteService() {
        return new QuoteService() {
            @Override
            public Single<TagListResponse> getTagList() {
                return null;
            }

            @Override
            public Single<QuoteListResponse> getQuotesForTag(String tagName) {
                return null;
            }
        };
    }

    private ArrayList<String> createMockTagList() {
        ArrayList<String> tagList = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            tagList.add(MOCK_TAG + i);
        }
        return tagList;
    }

    private ArrayList<Quote> createMockQuoteList() {
        ArrayList<Quote> quoteList = new ArrayList<>();
        Quote quote = new Quote(MOCK_QUOTE, MOCK_URL, MOCK_DATE, createMockTagList());
        for (int i = 0; i <5 ; i++) {
            quoteList.add(quote);

        }
        return quoteList;
    }



    @Override
    public void loadTagList(GetListCallback<String> callbackList) {
        callbackList.onDataListLoaded(createMockTagList());
    }

    @Override
    public void loadQuotesForTag(String tag, GetListCallback<Quote> callbackList) {
        callbackList.onDataListLoaded(createMockQuoteList());

    }

    @Override
    public ArrayList<String> fetchTagsList() {
        return createMockTagList();
    }

    @Override
    public void ifTagsNotCachedLoadThem(GetListCallback<String> callbackList) {
        callbackList.onDataListLoaded(createMockTagList());

    }

    @Override
    public String getTagForPosition(int position) {
        return MOCK_TAG;
    }

    @Override
    public void ifQuotesNotCachedLoadThem(String tag, GetListCallback<Quote> callbackList) {
        callbackList.onDataListLoaded(createMockQuoteList());

    }

    @Override
    public ArrayList<Quote> fetchQuoteList(String tag) {
        return createMockQuoteList();
    }

    @Override
    public Quote getQuoteForPosition(String tag, int position) {
        return new Quote(MOCK_QUOTE, MOCK_URL, MOCK_DATE, createMockTagList());
    }

    @Override
    public void setCurrentTag(String tag) {

    }

    @Override
    public String getCurrentTag() {
        return MOCK_TAG;
    }

    @Override
    public String getProperDate(String data) {
        return "22. Jun 1903";
    }

    @Override
    public void clear() {

    }
}
