package demo.igorzor.tdump.ui.tagcloud;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import demo.igorzor.tdump.R;
import demo.igorzor.tdump.data.DataSource;

public class TagCloudAdapter extends RecyclerView.Adapter<TagCloudAdapter.TagCloudAdapterViewHolder> {
    private static final String TAG = "TagCloudAdapter";

    private TagCloudContract.TagClickListener mListener;
    private DataSource mRepository;

    public TagCloudAdapter(TagCloudContract.TagClickListener listener, DataSource repository ) {
        this.mListener = listener;
        this.mRepository = repository;
    }

    @NonNull
    @Override
    public TagCloudAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_item, parent, false);
        return new TagCloudAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TagCloudAdapterViewHolder holder, int position) {
        holder.bind(mListener, mRepository);
    }

    @Override
    public void onViewRecycled(@NonNull TagCloudAdapterViewHolder holder) {
        super.onViewRecycled(holder);
        ((TagCloudAdapterViewHolder)holder).onViewRecycled();
    }

    @Override
    public int getItemCount() {
        return mRepository.fetchTagsList().size();
    }

    static class TagCloudAdapterViewHolder extends RecyclerView.ViewHolder implements TagCloudContract.ViewHolderView{
        private TextView mTagTv, mCommaTv;
        private TagCloudContract.ViewHolderPresenter mPresenter;

        private TagCloudContract.TagClickListener mListener;

        public TagCloudAdapterViewHolder(View itemView) {
            super(itemView);
        }

        void bind(TagCloudContract.TagClickListener listener, DataSource mRepository) {
            this.mListener = listener;
            itemView.setOnClickListener(v -> itemClicked(getAdapterPosition()));
            mPresenter = new TagCloudViewHolderPresenter(this,mRepository);
            initMvp();
        }

        private void itemClicked(int position) {
            mListener.onTagClick(position);
        }

        @Override
        public void initMvp() {
            mPresenter.create();
        }

        @Override
        public void initViews() {
            mTagTv = itemView.findViewById(R.id.tagcloud_tag_tv);
            mCommaTv = itemView.findViewById(R.id.tagcloud_comma_tv);
        }

        public void onViewRecycled() {
            mPresenter.destroy();
        }


        @Override
        public void setTag(String tag) {
            mTagTv.setText(tag);
        }

        @Override
        public void underlineTag() {
            mTagTv.setPaintFlags(mTagTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }

        @Override
        public void setCommaVisibility(boolean isHidden) {
            mCommaTv.setVisibility(isHidden? View.INVISIBLE:View.VISIBLE);
        }

        @Override
        public int getCurrentPosition() {
            return getAdapterPosition();
        }
    }
}
