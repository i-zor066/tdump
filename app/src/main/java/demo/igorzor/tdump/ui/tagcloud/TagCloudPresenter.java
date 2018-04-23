package demo.igorzor.tdump.ui.tagcloud;

import java.util.ArrayList;

import demo.igorzor.tdump.base.BaseView;
import demo.igorzor.tdump.data.DataSource;

public class TagCloudPresenter implements TagCloudContract.Presenter {

    private static final String TAG = "TagCloudPresenter";
    private DataSource mRepository;
    private TagCloudContract.View mView;

    public TagCloudPresenter(TagCloudContract.View mView, DataSource mRepository) {
        this.mRepository = mRepository;
        this.mView = mView;
    }

    @Override
    public void create() {
        if (mView != null & mRepository != null) {
            mView.setTitleBar();
            mView.setSwipeRefreshCallback();
            loadData();
        }
    }

    @Override
    public void loadData() {
        if (mView != null & mRepository != null) {
            mRepository.ifTagsNotCachedLoadThem(new DataSource.GetListCallback<String>() {
                @Override
                public void onDataListLoaded(ArrayList<String> callbackList) {
                    dataListLoaded(callbackList);
                }

                @Override
                public void onDataNotAvailable(String message) {
                    dataNotAvailable(message);
                }
            });
        }
    }

    @Override
    public void dataListLoaded(ArrayList<String> tagList) {
        if (mView != null & mRepository != null) {
            mView.setRecyclerView();
        }
    }

    @Override
    public void dataNotAvailable(String message) {
        if (mView!=null) {
            mView.displayNotification(message);
        }
    }

    @Override
    public void tagItemClicked(int position) {
        if (mView!=null) {
            mView.onTagItemClicked(position);
        }
    }

    @Override
    public void handleRefresh() {
        if (mView != null & mRepository != null) {
            mView.displayRefreshingNotification();
            mRepository.loadTagList(new DataSource.GetListCallback<String>() {
                @Override
                public void onDataListLoaded(ArrayList<String> tagList) {
                    if (mView != null) {
                        mView.refreshFinished();
                    }
                }

                @Override
                public void onDataNotAvailable(String message) {
                    if (mView != null) {
                        mView.refreshFinished();
                        mView.displayNotification(message);
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
