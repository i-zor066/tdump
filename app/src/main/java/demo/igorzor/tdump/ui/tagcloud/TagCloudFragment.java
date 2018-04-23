package demo.igorzor.tdump.ui.tagcloud;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaofeng.flowlayoutmanager.Alignment;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import javax.inject.Inject;

import demo.igorzor.tdump.R;
import demo.igorzor.tdump.TdumpApp;
import demo.igorzor.tdump.data.DataSource;
import demo.igorzor.tdump.ui.landing.LandingContract;

public class TagCloudFragment extends Fragment implements TagCloudContract.View {

    @Inject
    DataSource mRepository;

    private static final String TAG = "TagCloudFragment";
    private LandingContract.TagClickListener mTagClickListener;
    private TagCloudContract.Presenter mPresenter;
    private RecyclerView mRv;
    private TagCloudAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public TagCloudFragment() {
        // Required empty public constructor

    }


    public static TagCloudFragment newInstance() {
        TagCloudFragment fragment = new TagCloudFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tagcloud_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initMvp();
        Log.d(TAG, "onViewCreated: ");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TdumpApp.getSharedInstance().getAppComponent().inject(this);

        if (context instanceof LandingContract.TagClickListener) {
            mTagClickListener = (LandingContract.TagClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement LandingContract.TagClickListener");
        }
    }


    @Override
    public void initMvp() {
        mPresenter = new TagCloudPresenter(this, mRepository);
        mPresenter.create();
    }

    @Override
    public void initViews() {
        View view = getView();
        mRv = view.findViewById(R.id.tagcloud_rv);
        mSwipeRefreshLayout = view.findViewById(R.id.tagcloud_refresh_layout);
    }


    @Override
    public void setTitleBar() {
        ((TextView)getActivity().findViewById(R.id.toolbar_title)).setText(R.string.app_name);
        ((ImageView)getActivity().findViewById(R.id.toolbar_back)).setVisibility(View.GONE);
    }

    @Override
    public void setRecyclerView() {
        mAdapter = new TagCloudAdapter(this::tagItemClicked, mRepository);
        mRv.setLayoutManager(new FlowLayoutManager().setAlignment(Alignment.LEFT));
        mRv.setAdapter(mAdapter);
    }

    @Override
    public void setSwipeRefreshCallback() {
        mSwipeRefreshLayout.setOnRefreshListener(this::onSwipeToRefresh);
    }

    public void onSwipeToRefresh() {
        mPresenter.handleRefresh();
    }


    private void tagItemClicked(int position) {
       mPresenter.tagItemClicked(position);
    }

    @Override
    public void displayNotification(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTagItemClicked(int position) {
        mTagClickListener.onTagClick(position);
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

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resume();

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }


}
