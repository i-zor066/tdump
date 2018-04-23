package demo.igorzor.tdump.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import demo.igorzor.tdump.base.BaseSchedulerProvider;
import demo.igorzor.tdump.data.API.QuoteService;
import demo.igorzor.tdump.data.helpers.SchedulerProvider;
import demo.igorzor.tdump.data.helpers.StringFormatHelper;

import demo.igorzor.tdump.data.model.Quote;


import demo.igorzor.tdump.data.model.remote.taglistresponse.TagListResponse;
import io.reactivex.Observable;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Repository implements DataSource {
    private static final String TAG = "Repository";

    private QuoteService mService;
    private ArrayList<String> mTagList = new ArrayList<>();
    private HashMap<String,ArrayList<Quote>> mQuoteMap = new HashMap<>();
    private StringFormatHelper mStringFormatHelper;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private String mCurrentTag = "";
    private BaseSchedulerProvider mSchedulerProvider;

    public Repository(QuoteService mService, StringFormatHelper stringFormatHelper, BaseSchedulerProvider schedulerProvider) {
        this.mService = mService;
        this.mStringFormatHelper = stringFormatHelper;
        this.mSchedulerProvider = schedulerProvider;
    }

    @Override
    public QuoteService getQuoteService() {
        return mService;
    }

    @Override
    public void loadTagList(GetListCallback<String> callbackList) {
        mService.getTagList()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribeWith(new SingleObserver<TagListResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);

                    }

                    @Override
                    public void onSuccess(TagListResponse tagListResponse) {
                        mTagList = (ArrayList<String>) tagListResponse.getTagList();
                        callbackList.onDataListLoaded((ArrayList<String>) tagListResponse.getTagList());
//                        Log.d(TAG, Arrays.toString(tagListResponse.getTagList().toArray()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        callbackList.onDataNotAvailable(e.getMessage());
                    }
                });

    }

    @Override
    public void loadQuotesForTag(String tag, GetListCallback<Quote> callbackList) {
        mService.getQuotesForTag(tag).subscribeOn(mSchedulerProvider.io())
                .toObservable()
                .flatMap(quoteListResponse -> Observable.fromIterable(quoteListResponse.getEmbeddedFirst().getTags()))
                .map(tagsItem -> Quote.create(tagsItem))
                .toList()
                .observeOn(mSchedulerProvider.ui())
                .subscribeWith(new SingleObserver<List<Quote>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Quote> quotes) {
                        mQuoteMap.put(tag, (ArrayList<Quote>) quotes);
                        callbackList.onDataListLoaded((ArrayList<Quote>) quotes);
//                        Log.d(TAG, "onSuccess: size plus array " + quotes.size() +" " + Arrays.toString(quotes.toArray()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        callbackList.onDataNotAvailable(e.getMessage());
                        Log.e(TAG, "onError: ", e );
                    }
                });

    }

    @Override
    public ArrayList<String> fetchTagsList() {
        return mTagList;
    }

    @Override
    public void ifTagsNotCachedLoadThem(GetListCallback<String> callbackList) {
        if (mTagList.isEmpty())
            loadTagList(callbackList);
        else
            callbackList.onDataListLoaded(mTagList);
    }


    @Override
    public String getTagForPosition(int position) {
        return mTagList.get(position);
    }

    @Override
    public void ifQuotesNotCachedLoadThem(String tag, GetListCallback<Quote> callbackList) {
        if (!mQuoteMap.containsKey(tag))
            loadQuotesForTag(tag, callbackList);
        else
            callbackList.onDataListLoaded(mQuoteMap.get(tag));
    }

    @Override
    public ArrayList<Quote> fetchQuoteList(String tag) {
        return mQuoteMap.get(tag);
    }

    @Override
    public Quote getQuoteForPosition(String tag, int position) {
        return mQuoteMap.get(tag).get(position);
    }

    @Override
    public void setCurrentTag(String tag) {
        mCurrentTag = tag;
    }

    @Override
    public String getCurrentTag() {
        return mCurrentTag;
    }

    @Override
    public String getProperDate(String data) {
        return mStringFormatHelper.getProperDateFormat(data);
    }

    @Override
    public void clear() {
        mCompositeDisposable.clear();
    }

}
