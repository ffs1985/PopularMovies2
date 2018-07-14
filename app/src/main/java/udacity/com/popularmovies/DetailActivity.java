package udacity.com.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import udacity.com.popularmovies.model.FavoriteDatabase;
import udacity.com.popularmovies.model.Movie;
import udacity.com.popularmovies.model.MovieReview;
import udacity.com.popularmovies.model.MovieVideo;
import udacity.com.popularmovies.utils.MovieSearchType;
import udacity.com.popularmovies.utils.MoviesJsonUtil;
import udacity.com.popularmovies.utils.TheMovieDBUtils;

public class DetailActivity extends AppCompatActivity implements MovieTrailersAdapter.MovieTrailersAdapterOnClickHandler {
    private TextView tvTitle;
    private TextView tvDescription;
    private ImageView ivPoster;
    private RatingBar rvRating;
    private Movie movie;
    private static final String movieData = "movie_data";
    private boolean isFavorite = false;

    private RecyclerView mTrailersRecyclerView;
    private RecyclerView mReviewsRecyclerView;

    private MovieReviewsAdapter mMovieReviewsAdapter;
    private MovieTrailersAdapter mMovieTrailersAdapter;

    private FavoriteDatabase mDb;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        ivPoster = findViewById(R.id.ivPoster);
        rvRating = findViewById(R.id.ratingBar);
        rvRating.setMax(10);

        mDb = FavoriteDatabase.getsInstance(getApplicationContext());

        mTrailersRecyclerView = findViewById(R.id.recyclerview_movie_trailers);
        mReviewsRecyclerView = findViewById(R.id.recyclerview_movie_reviews);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            movie = getIntent().getParcelableExtra(movieData);
            tvTitle.setText(movie.getName());
            tvDescription.setText(movie.getDescription());
            Picasso.get().load(movie.getBackdropPath())
                    .placeholder(R.drawable.placeholder).into(ivPoster);
            rvRating.setRating(movie.getRating());

            LinearLayoutManager movieVideosLinearLayoutManager = new LinearLayoutManager(this);
            movieVideosLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            LinearLayoutManager movieReviewsLinearLayoutManager = new LinearLayoutManager(this);
            mTrailersRecyclerView.setLayoutManager(movieVideosLinearLayoutManager);
            mReviewsRecyclerView.setLayoutManager(movieReviewsLinearLayoutManager);

            mMovieReviewsAdapter = new MovieReviewsAdapter();
            mReviewsRecyclerView.setAdapter(mMovieReviewsAdapter);

            mMovieTrailersAdapter = new MovieTrailersAdapter(this);
            mTrailersRecyclerView.setAdapter(mMovieTrailersAdapter);

            loadVideosData(movie.getMovieId());
            loadReviewsData(movie.getMovieId());
        }
    }

    private void loadVideosData(String movieId) {
        mTrailersRecyclerView.setVisibility(View.VISIBLE);
        new DetailActivity.FetchVideosTask().execute(movieId);
    }

    private void loadReviewsData(String movieId) {
        mReviewsRecyclerView.setVisibility(View.VISIBLE);
        new DetailActivity.FetchReviewsTask().execute(movieId);
    }

    @Override
    public void onClick(MovieVideo movieVideo) {
        Context context = this;
        Uri uri = Uri.parse(movieVideo.getSite());
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + movieVideo.getKey()));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + movieVideo.getKey()));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    public class FetchVideosTask extends AsyncTask<String, Void, MovieVideo[]> {
        @Override
        protected MovieVideo[] doInBackground(String... params) {
            try {
                String jsonVideosResponse = TheMovieDBUtils.getMovieTrailers(params[0]);
                MovieVideo[] videosList = MoviesJsonUtil.convertJsonToMovieVideosList(jsonVideosResponse);
                return videosList;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
        @Override
        protected void onPostExecute(MovieVideo[] movieVideosListData) {
            if (movieVideosListData != null) {
                mMovieTrailersAdapter.setMovieVideosData(movieVideosListData);
            }
        }
    }

    public class FetchReviewsTask extends AsyncTask<String, Void, MovieReview[]> {
        @Override
        protected MovieReview[] doInBackground(String... params) {
            try {
                String jsonReviewsResponse = TheMovieDBUtils.getMovieReviews(params[0]);
                MovieReview[] reviewsList = MoviesJsonUtil.convertJsonToMovieReviewsList(jsonReviewsResponse);
                return reviewsList;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(MovieReview[] movieReviewListData) {
            if (movieReviewListData != null) {
                mMovieReviewsAdapter.setMovieReviewsData(movieReviewListData);
            }
        }
    }

    public class ToggleFavoriteTask extends AsyncTask<Void, Void, Void> {
        private Movie movie;
        private boolean addToFavorite;

        public ToggleFavoriteTask(Movie movie, boolean addToFavorite) {
            this.movie = movie;
            this.addToFavorite = addToFavorite;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                if (addToFavorite) {
                    mDb.movieDAO().insertMovie(movie);
                } else {
                    mDb.movieDAO().deleteMovie(movie);
                }
                updateFavoriteStatus(addToFavorite);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class LoadFavoriteTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            Movie favMovie = mDb.movieDAO().load(params[0]);
            try {
                isFavorite = favMovie != null;
                updateFavoriteStatus(isFavorite);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_detail, menu);
        if(isFavorite) {
            menu.getItem(0).setIcon(R.drawable.ic_favorite_white_48dp);
        } else {
            menu.getItem(0).setIcon(R.drawable.ic_favorite_border_white_48dp);
        }
        new LoadFavoriteTask().execute(movie.getMovieId());
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_favorite) {
            isFavorite = !isFavorite;
            updateFavorite();

        }
        return super.onOptionsItemSelected(item);
    }

    private void updateFavorite() {
        new DetailActivity.ToggleFavoriteTask(movie, isFavorite).execute();
    }

    private void updateFavoriteStatus(boolean isFav) {
        isFavorite = isFav;
        invalidateOptionsMenu();
    }
}
