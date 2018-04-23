package demo.igorzor.tdump.ui.landing;

import java.util.ArrayList;

import demo.igorzor.tdump.base.BaseView;
import demo.igorzor.tdump.data.DataSource;

public class LandingPresenter implements LandingContract.Presenter {

//    private static final String TAG = "LandingPresenter";
    private LandingContract.View mView;
    private DataSource mRepository;

    public LandingPresenter(LandingContract.View mView, DataSource mRepository) {
        this.mView = mView;
        this.mRepository = mRepository;
    }

    @Override
    public void create() {
        if (mView != null & mRepository != null) {
            mView.initViews();
            mView.setCustomToolbar();
            mView.showProgressBar();
            loadData();
        }
    }


    @Override
    public void loadData() {
        if (mView != null & mRepository != null) {
          mRepository.loadTagList(new DataSource.GetListCallback<String>() {
              @Override
              public void onDataListLoaded(ArrayList<String> tagList) {
                  dataListLoaded(tagList);
              }

              @Override
              public void onDataNotAvailable(String message) {
                  if (mView != null & mRepository != null) {
                      dataNotAvailable(message);
                  }

              }
          });
        }
    }

    @Override
    public void dataListLoaded(ArrayList<String> tagList) {
        if (mView != null & mRepository != null) {
            mView.hideProgressBar();
            mView.showEmptyState(tagList.isEmpty());
            if (!tagList.isEmpty())
                mView.showListFragment();
        }
    }

    @Override
    public void dataNotAvailable(String message) {
        if (mView != null & mRepository != null) {
            mView.hideProgressBar();
            mView.showEmptyState(true);
            mView.displayNotification(message);
        }
    }

    @Override
    public void handleOnTagClick(int position) {
        if (mView != null & mRepository != null) {
            mView.showDetailsFragment(position);
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
