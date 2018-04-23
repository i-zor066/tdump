package demo.igorzor.tdump.ui.details;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import demo.igorzor.tdump.data.DataSource;
import demo.igorzor.tdump.data.model.Quote;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class QuoteListViewHolderPresenterTest {

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock QuoteListContract.ViewHolderView mView;
    @Mock DataSource mDataSource;
    @Mock Quote mQuote;
    private static final String TAG_STRING = "testStringTag";
    private static final int POSITION = 4;
    private static final String QUOTE_TEXT= "150 chars or less of ...";
    private static final String DATE_STRING = "22/4/00";


    private QuoteListViewHolderPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        mPresenter = new QuoteListViewHolderPresenter(mView, mDataSource);
    }

    @Test
    public void whenPresenterIsCreatedViewsShouldBeInitialised() throws Exception {
        when(mView.getCurrentPosition()).thenReturn(POSITION);
        when(mDataSource.getCurrentTag()).thenReturn(TAG_STRING);
        when(mDataSource.getQuoteForPosition(TAG_STRING, POSITION)).thenReturn(mQuote);
        when(mQuote.getmQuoteValue()).thenReturn(QUOTE_TEXT);
        when(mQuote.getmDateAppearedAt()).thenReturn(DATE_STRING);
        when(mDataSource.getProperDate(DATE_STRING)).thenReturn(DATE_STRING);
        mPresenter.create();
        verify(mView, times(1)).initViews();
        verify(mView,times(1)).setQuote(QUOTE_TEXT);
        verify(mView,times(1)).setDate(DATE_STRING);
        verify(mView,times(1)).underlineSource();


    }

    @Test
    public void onDestroyReferencesToViewRepositoryShouldBeReleased() throws Exception {
        mPresenter.destroy();
        assertNull(mPresenter.getView());
        assertNull(mPresenter.getRepository());
    }
}