package demo.igorzor.tdump.ui.details;

import demo.igorzor.tdump.base.BasePresenter;
import demo.igorzor.tdump.base.BaseView;
import demo.igorzor.tdump.data.model.Quote;

public interface QuoteListContract {
    String TAG_POSITION = "TAG POSITION";

    interface View extends BaseView {
        void displayNotification(String message);
        int getPosition();
        void setToolbarTitle(String title);
        void setIntroText(String tag);
        void setRecyclerView();
        void onQuoteItemClicked(String url);
        void setSwipeRefreshCallback();
        void refreshFinished();
        void displayRefreshingNotification();
    }

    interface Presenter extends BasePresenter {
        void setNonListViews(String tag);
        void loadQuoteList(String currentTag);
        void setRecyclerView();
        void quoteItemClicked(int position);
        void handleRefresh();

    }

    interface SourceClickListener {
        void onSourceClick(int position);
    }

    interface ViewHolderView extends BaseView {
        void setQuote(String quote);
        void setDate(String date);
        void underlineSource();
        int getCurrentPosition();
    }

    interface ViewHolderPresenter extends BasePresenter {
        void setViewData(Quote quote);
    }

    interface OnBackButtonClickListener {
        void onBackButtonClick();
    }
}
