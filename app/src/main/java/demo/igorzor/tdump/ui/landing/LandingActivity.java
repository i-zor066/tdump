package demo.igorzor.tdump.ui.landing;

import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import demo.igorzor.tdump.R;
import demo.igorzor.tdump.TdumpApp;
import demo.igorzor.tdump.data.DataSource;
import demo.igorzor.tdump.ui.details.QuoteListContract;
import demo.igorzor.tdump.ui.details.QuoteListFragment;
import demo.igorzor.tdump.ui.tagcloud.TagCloudFragment;

public class LandingActivity extends AppCompatActivity implements LandingContract.View,
        LandingContract.TagClickListener, QuoteListContract.OnBackButtonClickListener {

    @Inject
    DataSource mRepository;

    private static final String TAG = "LandingActivity";
    private LandingContract.Presenter mPresenter;
    private ProgressBar mProgressBar;
    private TextView mEmptyState, mTitle;
    private FrameLayout mFragmentContainer;
    private ImageView mBackBtn;
    private View mToolbarBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        TdumpApp.getSharedInstance().getAppComponent().inject(this);
        initMvp();
    }

    @Override
    public void initMvp() {

        mPresenter = new LandingPresenter(this, mRepository);
        mPresenter.create();
    }

    @Override
    public void initViews() {
        mProgressBar = findViewById(R.id.progress_bar);
        mFragmentContainer = findViewById(R.id.fragment_container);
        mEmptyState = findViewById(R.id.empty_state);
        mTitle = findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.app_name);
        mBackBtn = findViewById(R.id.toolbar_back);
        mToolbarBg = findViewById(R.id.toolbar_background);
    }

    @Override
    public void setCustomToolbar() {
        mTitle.setText(R.string.app_name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();

    }

    @Override
    public void showProgressBar() {
        if  (mProgressBar != null) mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        if  (mProgressBar != null) mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyState(boolean showEmptyState) {
        mFragmentContainer.setVisibility(showEmptyState?View.GONE:View.VISIBLE);
        mEmptyState.setVisibility(showEmptyState?View.VISIBLE:View.GONE);
    }

    @Override
    public void showListFragment() {
        mFragmentContainer.setVisibility(View.VISIBLE);
        TagCloudFragment tagCloudFragment = getSupportFragmentManager().findFragmentByTag(LandingContract.LIST_FRAGMENT_TAG) == null?
                TagCloudFragment.newInstance():(TagCloudFragment) getSupportFragmentManager().findFragmentByTag(LandingContract.LIST_FRAGMENT_TAG);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, tagCloudFragment, LandingContract.LIST_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void showDetailsFragment(int position) {
        Log.d(TAG, "showDetailsFragment: CLICK " + System.nanoTime());
        QuoteListFragment quoteListFragment = QuoteListFragment.newInstance(position);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, quoteListFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void displayNotification(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onTagClick(int position) {
        mPresenter.handleOnTagClick(position);
    }


    @Override
    public void onBackButtonClick() {
        Log.d(TAG, "onBackButtonClick: ");
        getSupportFragmentManager().popBackStackImmediate();
        mBackBtn.setVisibility(View.GONE);
        mToolbarBg.setBackgroundColor(ContextCompat.getColor(mTitle.getContext(), R.color.default_silver));
        setStatusBarColour(R.color.default_silver);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: ");
        super.onBackPressed();
        mToolbarBg.setBackgroundColor(ContextCompat.getColor(mTitle.getContext(), R.color.default_silver));
        setStatusBarColour(R.color.default_silver);
    }

    @Override
    public void setStatusBarColour(int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(mTitle.getContext(), resId));
        }
    }


}
