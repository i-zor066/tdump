package demo.igorzor.tdump.ui.details;

import android.util.Log;

import java.util.ArrayList;

import demo.igorzor.tdump.base.BaseView;
import demo.igorzor.tdump.data.DataSource;
import demo.igorzor.tdump.data.model.Quote;

public class QuoteListPresenter implements QuoteListContract.Presenter {
    private static final String TAG = "DetailsPresenter";
    private DataSource mRepository;
    private QuoteListContract.View mView;

    public QuoteListPresenter(QuoteListContract.View mView, DataSource mRepository) {
        this.mRepository = mRepository;
        this.mView = mView;
    }

    @Override
    public void create() {
        if (mView != null & mRepository != null) {
            loadQuoteList(mRepository.getTagForPosition(mView.getPosition()));
            setNonListViews(mRepository.getTagForPosition(mView.getPosition()));
        }
    }

    @Override
    public void setNonListViews(String tag) {
        if (mView != null & mRepository != null) {
            mView.setToolbarTitle(tag);
            mView.setIntroText(tag);
            mView.setSwipeRefreshCallback();
        }
    }

    @Override
    public void loadQuoteList(String currentTag) {
        if (mView != null & mRepository != null) {
            mRepository.ifQuotesNotCachedLoadThem(currentTag, new DataSource.GetListCallback<Quote>() {
                @Override
                public void onDataListLoaded(ArrayList<Quote> callbackList) {
                    if (mView != null & mRepository != null) {
                        mRepository.setCurrentTag(currentTag);
                        setRecyclerView();
                    }
                }

                @Override
                public void onDataNotAvailable(String message) {
                    if (mView != null & mRepository != null) {
                        mRepository.setCurrentTag(currentTag);
                        mView.displayNotification(message);
                    }
                }
            });
        }

    }

    @Override
    public void setRecyclerView() {
        if (mView != null & mRepository != null) {
            mView.setRecyclerView();
        }
    }

    @Override
    public void quoteItemClicked(int position) {
        if (mView != null & mRepository != null) {
            String url = mRepository.getQuoteForPosition(mRepository.getCurrentTag(),position).getmQuoteUrl();
            mView.onQuoteItemClicked(url);
        }
    }

    @Override
    public void handleRefresh() {
        if (mView != null & mRepository != null) {
            mView.displayRefreshingNotification();
            mRepository.loadQuotesForTag(mRepository.getCurrentTag(), new DataSource.GetListCallback<Quote>() {
                @Override
                public void onDataListLoaded(ArrayList<Quote> callbackList) {
                    if (mView != null & mRepository != null) {
                        mView.refreshFinished();
                    }
                }

                @Override
                public void onDataNotAvailable(String message) {
                    if (mView != null & mRepository != null) {
                        mView.displayNotification(message);
                        mView.refreshFinished();
                    }
                }
            });
        }

    }

    @Override
    public void destroy() {
        if (mView != null & mRepository != null) {
            mRepository.clear();
            mView = null;
            mRepository = null;
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public BaseView getView() {
        return mView;
    }

    @Override
    public DataSource getRepository() {
        return mRepository;
    }


}
