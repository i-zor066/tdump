package demo.igorzor.tdump.ui.landing;

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

public class LandingPresenterTest {

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock LandingContract.View mView;
    @Mock DataSource mDataSource;
    @Captor ArgumentCaptor<DataSource.GetListCallback<String>> captor;


    private ArrayList<String> mTagList;
    private ArrayList<String> mEmptyList;
    private LandingPresenter mPresenter;

    private static final String DATA_NOT_AVAILABLE = "Data not available!";
    private static final int POSITION = 4;
    private static final String TAG_STRING = "testStringTag";



    @Before
    public void setUp() throws Exception {
        mPresenter = new LandingPresenter(mView, mDataSource);
        mTagList = new ArrayList<>();
        mEmptyList = new ArrayList<>();
        mTagList.add(TAG_STRING);
    }


    @Test
    public void whenPresenterIsCreatedViewsShouldBeInitialised() throws Exception {
        mPresenter.create();
        verify(mView, times(1)).initViews();
    }

    @Test
    public void whenPresenterIsCreatedCustomToolbarShouldBeSet() throws Exception {
        mPresenter.create();
        verify(mView, times(1)).setCustomToolbar();
    }

    @Test
    public void whenPresenterIsCreatedProgressBarShouldBeShown() throws Exception {
        mPresenter.create();
        verify(mView, times(1)).showProgressBar();
    }

    @Test
    public void whenPresenterIsCreatedDataShouldStartToLoad() throws Exception {
        mPresenter.create();
        verify(mDataSource, times(1)).loadTagList(any());
    }

    @Test
    public void whenDataIsAvailableProgressBarShouldBeHidden() throws Exception {
        mPresenter.loadData();
        verify(mDataSource).loadTagList(captor.capture());
        captor.getValue().onDataListLoaded(mTagList);
        verify(mView,times(1)).hideProgressBar();
    }

    @Test
    public void whenDataIsAvailableEmptyStateShouldBeHidden() throws Exception {
        mPresenter.loadData();
        verify(mDataSource).loadTagList(captor.capture());
        captor.getValue().onDataListLoaded(mTagList);
        verify(mView,times(1)).showEmptyState(false);
    }

    @Test
    public void whenDataIsAvailableListFragmentShouldShow() throws Exception {
        mPresenter.loadData();
        verify(mDataSource).loadTagList(captor.capture());
        captor.getValue().onDataListLoaded(mTagList);
        verify(mView,times(1)).showListFragment();
    }

    @Test
    public void whenDatalistIsEmptyEmptyStateShouldBeShown() throws Exception {
        mPresenter.loadData();
        verify(mDataSource).loadTagList(captor.capture());
        captor.getValue().onDataListLoaded(mEmptyList);
        verify(mView,times(1)).showEmptyState(true);
    }

    @Test
    public void whenDatalistIsEmptyListFragmentShouldntShow() throws Exception {
        mPresenter.loadData();
        verify(mDataSource).loadTagList(captor.capture());
        captor.getValue().onDataListLoaded(mEmptyList);
        verify(mView,times(0)).showListFragment();
    }

    @Test
    public void whenDataIsNotAvailableNotificationShouldShow() throws Exception {
        mPresenter.loadData();
        verify(mDataSource).loadTagList(captor.capture());
        captor.getValue().onDataNotAvailable(DATA_NOT_AVAILABLE);
        verify(mView,times(1)).displayNotification(DATA_NOT_AVAILABLE);
    }

    @Test
    public void whenDataIsNotAvailableEmptyStateShouldShow() throws Exception {
        mPresenter.loadData();
        verify(mDataSource).loadTagList(captor.capture());
        captor.getValue().onDataNotAvailable(DATA_NOT_AVAILABLE);
        verify(mView,times(1)).showEmptyState(true);
    }

    @Test
    public void whenDataIsNotAvailableProgressBarShouldBeHidden() throws Exception {
        mPresenter.loadData();
        verify(mDataSource).loadTagList(captor.capture());
        captor.getValue().onDataNotAvailable(DATA_NOT_AVAILABLE);
        verify(mView,times(1)).hideProgressBar();
    }

    @Test
    public void whenTagClickIsHandledDetailsFragmentShouldShow() throws Exception {
        mPresenter.handleOnTagClick(POSITION);
        verify(mView, times(1)).showDetailsFragment(POSITION);

    }

    @Test
    public void onDestroyReferencesToViewRepositoryShouldBeReleased() throws Exception {
        mPresenter.destroy();
        assertNull(mPresenter.getView());
        assertNull(mPresenter.getRepository());
    }
}