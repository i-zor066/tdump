package demo.igorzor.tdump.ui.tagcloud;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;

import demo.igorzor.tdump.data.DataSource;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TagCloudViewHolderPresenterTest {

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock TagCloudContract.ViewHolderView mView;
    @Mock DataSource mDataSource;
    @Mock ArrayList<String> mTagList;
    private static final String TAG_STRING = "testStringTag";
    private static final int POSITION = 4;



    private TagCloudViewHolderPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        mPresenter = new TagCloudViewHolderPresenter(mView, mDataSource);
    }

    @Test
    public void whenPresenterIsCreatedViewsShouldBeInitialised() throws Exception {
        when(mView.getCurrentPosition()).thenReturn(POSITION);
        when(mDataSource.getTagForPosition(POSITION)).thenReturn(TAG_STRING);
        mPresenter.create();
        verify(mView, times(1)).initViews();
    }

    @Test
    public void whenPresenterIsCreatedViewDataShouldBeSet() throws Exception {
        when(mView.getCurrentPosition()).thenReturn(POSITION);
        when(mDataSource.getTagForPosition(POSITION)).thenReturn(TAG_STRING);
        mPresenter.create();
        verify(mView, times(1)).setTag(TAG_STRING);
        verify(mView, times(1)).underlineTag();
        verify(mView, times(1)).setCommaVisibility(anyBoolean());
    }

    @Test
    public void ifEntryIsTheFinalOneCommaShouldBeHidden() throws Exception {
        when(mView.getCurrentPosition()).thenReturn(POSITION);
        when(mDataSource.getTagForPosition(POSITION)).thenReturn(TAG_STRING);
        when(mDataSource.fetchTagsList()).thenReturn(mTagList);
        when(mTagList.size()).thenReturn(POSITION+1);
        mPresenter.create();
        verify(mView, times(1)).setCommaVisibility(true);
    }


    @Test
    public void onDestroyReferencesToViewRepositoryShouldBeReleased() throws Exception {
        mPresenter.destroy();
        assertNull(mPresenter.getView());
        assertNull(mPresenter.getRepository());
    }

}