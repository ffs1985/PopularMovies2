package udacity.com.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import udacity.com.popularmovies.model.MovieVideo;

/**
 * Created by federicofontes on 23/6/18.
 */

public class MovieTrailersAdapter extends RecyclerView.Adapter<MovieTrailersAdapter.MovieTrailersAdapterViewHolder> {
    private MovieVideo[] mMovieVideoListData;

    private final MovieTrailersAdapter.MovieTrailersAdapterOnClickHandler mClickHandler;

    public interface MovieTrailersAdapterOnClickHandler {
        void onClick(MovieVideo movieVideo);
    }

    public MovieTrailersAdapter(MovieTrailersAdapter.MovieTrailersAdapterOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }

    public class MovieTrailersAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mMovieTextView;
        public final ImageView mMovieImageView;

        public MovieTrailersAdapterViewHolder(View view) {
            super(view);
            mMovieTextView = view.findViewById(R.id.movie_trailer_name);
            mMovieImageView = view.findViewById(R.id.movie_thumbnail_image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            MovieVideo movieVideo = mMovieVideoListData[adapterPosition];
            mClickHandler.onClick(movieVideo);
        }
    }

    @Override
    public MovieTrailersAdapter.MovieTrailersAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.video_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new MovieTrailersAdapter.MovieTrailersAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieTrailersAdapter.MovieTrailersAdapterViewHolder forecastAdapterViewHolder, int position) {
        MovieVideo movieVideo = mMovieVideoListData[position];
        forecastAdapterViewHolder.mMovieTextView.setText(movieVideo.getName());
        Picasso.get().load(movieVideo.getThumbnailImage())
                .placeholder(R.drawable.ic_play).into(forecastAdapterViewHolder.mMovieImageView);
    }

    @Override
    public int getItemCount() {
        if (null == mMovieVideoListData) return 0;
        return mMovieVideoListData.length;
    }

    public void setMovieVideosData(MovieVideo[] movieVideoData) {
        mMovieVideoListData = movieVideoData;
        notifyDataSetChanged();
    }
}
