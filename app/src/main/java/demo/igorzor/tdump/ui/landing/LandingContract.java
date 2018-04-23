package demo.igorzor.tdump.ui.landing;

import java.util.ArrayList;

import demo.igorzor.tdump.base.BasePresenter;
import demo.igorzor.tdump.base.BaseView;
import demo.igorzor.tdump.data.DataSource;

public interface LandingContract {

    String LIST_FRAGMENT_TAG = "LIST_FRAGMENT_TAG";

    interface View extends BaseView {
        void showProgressBar();
        void hideProgressBar();
        void showEmptyState(boolean showEmptyState);
        void showListFragment();
        void showDetailsFragment(int position);
        void displayNotification(String text);
        void setCustomToolbar();
        void setStatusBarColour(int resId);
    }

    interface Presenter extends BasePresenter {
        void loadData();
        void dataListLoaded(ArrayList<String> tagList);
        void dataNotAvailable(String message);
        void handleOnTagClick(int position);
    }

    interface FragmentListener {
        DataSource getRepository(); // provides repository to the instantiated fragments
    }

    interface TagClickListener {
        void onTagClick(int position);
    }


}
