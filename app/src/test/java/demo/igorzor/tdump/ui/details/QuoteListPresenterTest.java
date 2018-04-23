package demo.igorzor.tdump.ui.details;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;

import demo.igorzor.tdump.data.DataSource;
import demo.igorzor.tdump.data.model.Quote;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class QuoteListPresenterTest {

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock QuoteListContract.View mView;
    @Mock DataSource mDataSource;

    @Mock Quote mQuote;
    @Captor ArgumentCaptor<DataSource.GetListCallback<Quote>> captor;


    private ArrayList<Quote> mQuoteList;

    private static final int POSITION = 4;
    private static final String TAG_STRING = "tagString";
    private static final String DATA_NOT_AVAILABLE = "Data not available!";


    private QuoteListPresenter mPresenter;


    @Before
    public void setUp() throws Exception {
        mPresenter = new QuoteListPresenter(mView, mDataSource);
        mQuoteList = new ArrayList<>();
        mQuoteList.add(mQuote);
    }

    @Test
    public void whenPresenterIsCreatedTitleBarShouldBeSet() throws Exception {
        when(mView.getPosition()).thenReturn(POSITION);
        when(mDataSource.getTagForPosition(POSITION)).thenReturn(TAG_STRING);
        mPresenter.create();
        verify(mView, times(1)).setToolbarTitle(TAG_STRING);
    }

    @Test
    public void whenPresenterIsCreatedsetSwipeRefreshCallbackShouldBeSet() throws Exception {
        mPresenter.create();
        verify(mView, times(1)).setSwipeRefreshCallback();
    }

    @Test
    public void whenPresenterIsCreatedTagShouldBeRetrievedFromDatasourceTwice() throws Exception {
        mPresenter.create();
        verify(mDataSource, times(2)).getTagForPosition(anyInt());
    }


    @Test
    public void whenPresenterIsCreatedDataShouldStartToLoad() throws Exception {
        mPresenter.create();
        verify(mDataSource, times(1)).ifQuotesNotCachedLoadThem(any(),any());
    }

    @Test
    public void whenPresenterIsCreatedNonListViewsShouldBeSet() throws Exception {
        when(mView.getPosition()).thenReturn(POSITION);
        when(mDataSource.getTagForPosition(POSITION)).thenReturn(TAG_STRING);
        mPresenter.create();
        verify(mView, times(1)).setToolbarTitle(TAG_STRING);
        verify(mView, times(1)).setIntroText(TAG_STRING);

    }

    @Test
    public void whenPresenterIsCreatedSwipeRefreshCallbackShouldBeSet() throws Exception {
        when(mView.getPosition()).thenReturn(POSITION);
        when(mDataSource.getTagForPosition(POSITION)).thenReturn(TAG_STRING);
        mPresenter.create();
        verify(mView, times(1)).setSwipeRefreshCallback();

    }

    @Test
    public void whenDataIsAvailableRecyclerViewShouldBeSet() throws Exception {
        mPresenter.loadQuoteList(TAG_STRING);
        verify(mDataSource).ifQuotesNotCachedLoadThem(anyString(),captor.capture());
        captor.getValue().onDataListLoaded(mQuoteList);
        verify(mView,times(1)).setRecyclerView();
    }

    @Test
    public void whenDataIsNotAvailableNotificationShouldShow() throws Exception {
        mPresenter.loadQuoteList(TAG_STRING);
        verify(mDataSource).ifQuotesNotCachedLoadThem(anyString(),captor.capture());
        captor.getValue().onDataNotAvailable(DATA_NOT_AVAILABLE);
        verify(mView,times(1)).displayNotification(DATA_NOT_AVAILABLE);
    }

    @Test
    public void whenHandlingRefreshNotificationShouldShow() throws Exception {
        mPresenter.handleRefresh();
        verify(mView, times(1)).displayRefreshingNotification();
    }

    @Test
    public void whenHandlingRefreshDataListShouldReloadShow() throws Exception {
        mPresenter.handleRefresh();
        verify(mDataSource, times(1)).loadQuotesForTag(any(),any());
    }

    @Test
    public void whenDataIsAvailableRefreshShouldStop() throws Exception {
        mPresenter.handleRefresh();
        verify(mDataSource).loadQuotesForTag(any(),captor.capture());
        captor.getValue().onDataListLoaded(mQuoteList);
        verify(mView,times(1)).refreshFinished();
    }

    @Test
    public void whenDataIsNotAvailableForRefreshNotificationShouldShow() throws Exception {
        mPresenter.handleRefresh();
        verify(mDataSource).loadQuotesForTag(any(),captor.capture());
        captor.getValue().onDataNotAvailable(DATA_NOT_AVAILABLE);
        verify(mView,times(1)).displayNotification(DATA_NOT_AVAILABLE);
    }

    @Test
    public void whenDataIsNotAvailableRefreshShouldStop() throws Exception {
        mPresenter.handleRefresh();
        verify(mDataSource).loadQuotesForTag(any(),captor.capture());
        captor.getValue().onDataNotAvailable(DATA_NOT_AVAILABLE);
        verify(mView,times(1)).refreshFinished();
    }

    @Test
    public void onDestroyReferencesToViewRepositoryShouldBeReleased() throws Exception {
        mPresenter.destroy();
        assertNull(mPresenter.getView());
        assertNull(mPresenter.getRepository());
    }

}