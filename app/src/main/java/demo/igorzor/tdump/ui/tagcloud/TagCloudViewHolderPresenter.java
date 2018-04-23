package demo.igorzor.tdump.ui.tagcloud;

import demo.igorzor.tdump.base.BaseView;
import demo.igorzor.tdump.data.DataSource;

public class TagCloudViewHolderPresenter implements TagCloudContract.ViewHolderPresenter {

    private static final String TAG = "TagCloudViewHolderPresenter";
    private TagCloudContract.ViewHolderView mView;
    private DataSource mRepository;

    public TagCloudViewHolderPresenter(TagCloudContract.ViewHolderView mView, DataSource mRepository) {
        this.mView = mView;
        this.mRepository = mRepository;
    }

    @Override
    public void create() {
        if (mView != null & mRepository != null) {
            mView.initViews();
            setViewData(mRepository.getTagForPosition(mView.getCurrentPosition()));
        }
    }

    @Override
    public void setViewData(String tag) {
        if (mView != null & mRepository != null) {
            mView.setTag(tag);
            mView.underlineTag();
            mView.setCommaVisibility(mView.getCurrentPosition() == mRepository.fetchTagsList().size()-1);
        }
    }


    @Override
    public void destroy() {
        mView = null;
        mRepository = null;
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
