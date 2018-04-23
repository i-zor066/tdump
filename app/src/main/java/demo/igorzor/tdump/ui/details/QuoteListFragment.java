package demo.igorzor.tdump.ui.details;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import javax.inject.Inject;

import demo.igorzor.tdump.R;
import demo.igorzor.tdump.TdumpApp;
import demo.igorzor.tdump.data.DataSource;
import demo.igorzor.tdump.ui.landing.LandingContract;

public class QuoteListFragment extends Fragment implements LandingContract.FragmentListener,QuoteListContract.View {
    @Inject
    DataSource mRepository;
    private static final String TAG = "DetailsFragment";
    private QuoteListContract.Presenter mPresenter;
    private int mPosition;
    private TextView mTitle, mIntroText;
    private View mTitleBg;
    private ImageView mTitleBackBtn;
    private View mCardViewHeader;
    private RecyclerView mRv;
    private QuoteListAdapter mAdapter;
    private QuoteListContract.OnBackButtonClickListener onBackButtonClickListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    public QuoteListFragment() {
    }

    public static QuoteListFragment newInstance(int position) {
        QuoteListFragment fragment = new QuoteListFragment();
        Bundle args = new Bundle();
        args.putInt(QuoteListContract.TAG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TdumpApp.getSharedInstance().getAppComponent().inject(this);

        if (context instanceof QuoteListContract.OnBackButtonClickListener) {
            onBackButtonClickListener = (QuoteListContract.OnBackButtonClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement LandingContract.FragmentListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt(QuoteListContract.TAG_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.quote_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initMvp();
    }



    @Override
    public void displayNotification(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getPosition() {
        return mPosition;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void initMvp() {
        mPresenter = new QuoteListPresenter(this, mRepository);
        mPresenter.create();
    }

    @Override
    public void initViews() {
        View view = getView();
        mTitle = (getActivity()).findViewById(R.id.toolbar_title);
        mTitleBackBtn = (getActivity()).findViewById(R.id.toolbar_back);
        mTitleBg = (getActivity()).findViewById(R.id.toolbar_background);
        mRv = view.findViewById(R.id.quotelist_rv);
        mSwipeRefreshLayout = view.findViewById(R.id.quote_list_refresh_layout);
        mIntroText = view.findViewById(R.id.text_intro_tv);
        mCardViewHeader = view.findViewById(R.id.view_001);
        mTitleBackBtn.setVisibility(View.VISIBLE);
        mTitleBackBtn.setOnClickListener(this::onBackButtonClick);
    }

    @Override
    public void setRecyclerView() {
        mAdapter = new QuoteListAdapter(this::quoteItemClicked, mRepository);
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRv.setAdapter(mAdapter);
    }

    @Override
    public void onQuoteItemClicked(String url) {
        Intent openLinkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(openLinkIntent);
    }

    @Override
    public void setSwipeRefreshCallback() {
        mSwipeRefreshLayout.setOnRefreshListener(this::onSwipeToRefresh);
    }

    public void onSwipeToRefresh() {
        mPresenter.handleRefresh();
    }

    @Override
    public void refreshFinished() {
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayRefreshingNotification() {
        displayNotification(getContext().getString(R.string.refreshing));
    }


    public void onBackButtonClick(View view) {
        onBackButtonClickListener.onBackButtonClick();
    }

    private void quoteItemClicked(int position) {
        mPresenter.quoteItemClicked(position);
    }

    @Override
    public void setToolbarTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void setIntroText(String tag) {
        mIntroText.setText(getString(R.string.what_tronald_thinks_about, tag));
    }

    @Override
    public DataSource getRepository() {
        return mRepository;
    }


}
