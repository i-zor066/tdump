package demo.igorzor.tdump.data;

import java.util.ArrayList;

import demo.igorzor.tdump.data.API.QuoteService;
import demo.igorzor.tdump.data.model.Quote;
import demo.igorzor.tdump.data.model.remote.quotelistresponse.TagsItem;

public interface DataSource {

    interface GetListCallback<T> {
        void onDataListLoaded(ArrayList<T> callbackList);
        void onDataNotAvailable(String message);
    }

    QuoteService getQuoteService();
    void loadTagList(GetListCallback<String> callbackList);
    void loadQuotesForTag(String tag, GetListCallback<Quote> callbackList);
    ArrayList<String> fetchTagsList();
    void ifTagsNotCachedLoadThem(GetListCallback<String> callbackList);
    String getTagForPosition(int position);
    void ifQuotesNotCachedLoadThem(String tag, GetListCallback<Quote> callbackList);
    ArrayList<Quote> fetchQuoteList(String tag);
    Quote getQuoteForPosition(String tag, int position);
    void setCurrentTag(String tag);
    String getCurrentTag();
    String getProperDate(String data);
    void clear();


}
