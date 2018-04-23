package demo.igorzor.tdump.ui.details;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import demo.igorzor.tdump.R;
import demo.igorzor.tdump.data.DataSource;
import demo.igorzor.tdump.ui.tagcloud.TagCloudAdapter;
import demo.igorzor.tdump.ui.tagcloud.TagCloudContract;
import demo.igorzor.tdump.ui.tagcloud.TagCloudViewHolderPresenter;

public class QuoteListAdapter extends RecyclerView.Adapter<QuoteListAdapter.QuoteListViewHolder > {

    private static final String TAG = "QuoteListAdapter";

    private QuoteListContract.SourceClickListener mListener;
    private DataSource mRepository;

    public QuoteListAdapter(QuoteListContract.SourceClickListener listener, DataSource repository ) {
        this.mListener = listener;
        this.mRepository = repository;
    }

    @NonNull
    @Override
    public QuoteListAdapter.QuoteListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_item, parent, false);
        return new QuoteListAdapter.QuoteListViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteListAdapter.QuoteListViewHolder   holder, int position) {
        holder.bind(mListener, mRepository);
    }



    @Override
    public void onViewRecycled(@NonNull QuoteListAdapter.QuoteListViewHolder  holder) {
        super.onViewRecycled(holder);
        ((QuoteListAdapter.QuoteListViewHolder )holder).onViewRecycled();
    }

    @Override
    public int getItemCount() {
        return mRepository.fetchQuoteList(mRepository.getCurrentTag()).size();
    }

    static class QuoteListViewHolder extends RecyclerView.ViewHolder implements QuoteListContract.ViewHolderView{
        private TextView mQuoteTv, mDateTv, mSourceTv;
        private QuoteListContract.ViewHolderPresenter mPresenter;

        private QuoteListContract.SourceClickListener mListener;

        public QuoteListViewHolder(View itemView) {
            super(itemView);
        }

        void bind( QuoteListContract.SourceClickListener listener, DataSource mRepository) {
            this.mListener = listener;
            itemView.setOnClickListener(v -> itemClicked(getAdapterPosition()));
            mPresenter = new QuoteListViewHolderPresenter(this,mRepository);
            initMvp();
        }

        private void itemClicked(int position) {
            mListener.onSourceClick(position);
        }

        @Override
        public void initMvp() {
            mPresenter.create();
        }

        @Override
        public void initViews() {
            mQuoteTv = itemView.findViewById(R.id.quote_tv);
            mDateTv = itemView.findViewById(R.id.quote_date_tv);
            mSourceTv = itemView.findViewById(R.id.quote_source_tv);
        }

        public void onViewRecycled() {
            mPresenter.destroy();
        }


        @Override
        public void setQuote(String quote) {
            mQuoteTv.setText(quote);
        }

        @Override
        public void setDate(String date) {
            mDateTv.setText(date);
        }

        @Override
        public void underlineSource() {
            mSourceTv.setPaintFlags(mSourceTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }


        @Override
        public int getCurrentPosition() {
            return getAdapterPosition();
        }
    }
}
