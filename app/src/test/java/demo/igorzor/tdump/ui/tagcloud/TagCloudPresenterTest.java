package demo.igorzor.tdump.ui.tagcloud;

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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TagCloudPresenterTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    TagCloudContract.View mView;
    @Mock DataSource mDataSource;
    private static final String TAG_STRING = "tagString";
    @Captor ArgumentCaptor<DataSource.GetListCallback<String>> captor;

    private ArrayList<String> mTagList;

    private static final String DATA_NOT_AVAILABLE = "Data not available!";



    private TagCloudPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        mPresenter = new TagCloudPresenter(mView, mDataSource);
        mTagList = new ArrayList<>();
        mTagList.add(TAG_STRING);
    }

    @Test
    public void whenPresenterIsCreatedTitleBarShouldBeSet() throws Exception {
        mPresenter.create();
        verify(mView, times(1)).setTitleBar();
    }

    @Test
    public void whenPresenterIsCreatedsetSwipeRefreshCallbackShouldBeSet() throws Exception {
        mPresenter.create();
        verify(mView, times(1)).setSwipeRefreshCallback();
    }

    @Test
    public void whenPresenterIsCreatedDataShouldStartToLoad() throws Exception {
        mPresenter.create();
        verify(mDataSource, times(1)).ifTagsNotCachedLoadThem(any());
    }

    @Test
    public void whenDataIsAvailableRecyclerViewShouldBeSet() throws Exception {
        mPresenter.loadData();
        verify(mDataSource).ifTagsNotCachedLoadThem(captor.capture());
        captor.getValue().onDataListLoaded(mTagList);
        verify(mView,times(1)).setRecyclerView();
    }

    @Test
    public void whenDataIsNotAvailableNotificationShouldShow() throws Exception {
        mPresenter.loadData();
        verify(mDataSource).ifTagsNotCachedLoadThem(captor.capture());
        captor.getValue().onDataNotAvailable(DATA_NOT_AVAILABLE);
        verify(mView,times(1)).displayNotification(DATA_NOT_AVAILABLE);
    }

    @Test
    public void whenHandlingRefreshNotificationShouldShow() throws Exception {
        mPresenter.handleRefresh();
        verify(mView, times(1)).displayRefreshingNotification();
    }

    @Test
    public void whenHandlingRefreshTagsShouldLoad() throws Exception {
        mPresenter.handleRefresh();
        verify(mDataSource, times(1)).loadTagList(any());
    }

    @Test
    public void whenDataIsAvailableRefreshShouldStop() throws Exception {
        mPresenter.handleRefresh();
        verify(mDataSource).loadTagList(captor.capture());
        captor.getValue().onDataListLoaded(mTagList);
        verify(mView,times(1)).refreshFinished();
    }

    @Test
    public void whenDataIsNotAvailableForRefreshNotificationShouldShow() throws Exception {
        mPresenter.handleRefresh();
        verify(mDataSource).loadTagList(captor.capture());
        captor.getValue().onDataNotAvailable(DATA_NOT_AVAILABLE);
        verify(mView,times(1)).displayNotification(DATA_NOT_AVAILABLE);
    }

    @Test
    public void whenDataIsNotAvailableRefreshShouldStop() throws Exception {
        mPresenter.handleRefresh();
        verify(mDataSource).loadTagList(captor.capture());
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