package udacity.com.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.List;

import udacity.com.popularmovies.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private Movie[] mMovieListData;

    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MovieAdapter(MovieAdapterOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mMovieTextView;
        public final ImageView mMovieImageView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mMovieTextView = view.findViewById(R.id.movie_name);
            mMovieImageView = view.findViewById(R.id.movie_image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMovieListData[adapterPosition];
            mClickHandler.onClick(movie);
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder forecastAdapterViewHolder, int position) {
        Movie movie = mMovieListData[position];
        forecastAdapterViewHolder.mMovieTextView.setText(movie.getName());
        Picasso.get().load(movie.getImagePath())
                .placeholder(R.drawable.placeholder).into(forecastAdapterViewHolder.mMovieImageView);
    }

    @Override
    public int getItemCount() {
        if (null == mMovieListData) return 0;
        return mMovieListData.length;
    }

    public void setMovieData(List<Movie> movieData) {
        Movie[] moviesArray = null;
        if (movieData != null) {
            moviesArray = new Movie[movieData.size()];
            moviesArray = movieData.toArray(moviesArray);
        }
        mMovieListData = moviesArray;
        notifyDataSetChanged();

    }
}
