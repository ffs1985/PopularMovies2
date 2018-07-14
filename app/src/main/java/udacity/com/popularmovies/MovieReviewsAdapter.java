package udacity.com.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import udacity.com.popularmovies.model.MovieReview;

/**
 * Created by federicofontes on 23/6/18.
 */

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.MovieReviewsAdapterViewHolder> {
    private MovieReview[] mMovieReviewListData;

    public interface MovieReviewsAdapterOnClickHandler {
        void onClick(MovieReview movieReview);
    }

    public MovieReviewsAdapter() {
    }

    public class MovieReviewsAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView mMovieReviewAuthorTextView;
        public final TextView mMovieReviewContentImageView;

        public MovieReviewsAdapterViewHolder(View view) {
            super(view);
            mMovieReviewAuthorTextView = view.findViewById(R.id.movie_review_author);
            mMovieReviewContentImageView = view.findViewById(R.id.movie_review_content);
        }
    }

    @Override
    public MovieReviewsAdapter.MovieReviewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.review_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new MovieReviewsAdapter.MovieReviewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieReviewsAdapter.MovieReviewsAdapterViewHolder forecastAdapterViewHolder, int position) {
        MovieReview movieReview = mMovieReviewListData[position];
        forecastAdapterViewHolder.mMovieReviewAuthorTextView.setText(movieReview.getAuthor());
        forecastAdapterViewHolder.mMovieReviewContentImageView.setText(movieReview.getContent());
    }

    @Override
    public int getItemCount() {
        if (null == mMovieReviewListData) return 0;
        return mMovieReviewListData.length;
    }

    public void setMovieReviewsData(MovieReview[] movieReviewData) {
        mMovieReviewListData = movieReviewData;
        notifyDataSetChanged();
    }
}
