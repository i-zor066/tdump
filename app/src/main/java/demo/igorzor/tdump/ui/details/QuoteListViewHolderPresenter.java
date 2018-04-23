package demo.igorzor.tdump.ui.details;

import demo.igorzor.tdump.base.BaseView;
import demo.igorzor.tdump.data.DataSource;
import demo.igorzor.tdump.data.model.Quote;

public class QuoteListViewHolderPresenter implements QuoteListContract.ViewHolderPresenter {

    private QuoteListContract.ViewHolderView mView;
    private DataSource mRepository;

    public QuoteListViewHolderPresenter(QuoteListContract.ViewHolderView view, DataSource mRepository) {
        this.mView = view;
        this.mRepository = mRepository;
    }

    @Override
    public void create() {
        if (mView != null & mRepository != null) {
            mView.initViews();
            setViewData(mRepository.getQuoteForPosition(mRepository.getCurrentTag(),mView.getCurrentPosition()));
        }
    }

    @Override
    public void setViewData(Quote quote) {
        if (mView != null & mRepository != null) {
            mView.setQuote(quote.getmQuoteValue());
            mView.setDate(mRepository.getProperDate(quote.getmDateAppearedAt()));
            mView.underlineSource();

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
