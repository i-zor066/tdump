package demo.igorzor.tdump.ui.tagcloud;

import java.util.ArrayList;

import demo.igorzor.tdump.base.BasePresenter;
import demo.igorzor.tdump.base.BaseView;

public interface TagCloudContract {

    interface View extends BaseView {
        void setTitleBar();
        void setRecyclerView();
        void setSwipeRefreshCallback();
        void displayNotification(String message);
        void onTagItemClicked(int position);
        void refreshFinished();
        void displayRefreshingNotification();
    }

    interface Presenter extends BasePresenter {
        void loadData();
        void dataListLoaded(ArrayList<String> tagList);
        void dataNotAvailable(String message);
        void tagItemClicked(int position);
        void handleRefresh();
    }

    interface TagClickListener {
        void onTagClick(int position);
    }


    interface ViewHolderView extends BaseView {
        void setTag(String tag);
        void underlineTag();
        void setCommaVisibility(boolean isHidden);
        int getCurrentPosition();
    }

    interface ViewHolderPresenter extends BasePresenter {
        void setViewData(String tag);
    }
}
