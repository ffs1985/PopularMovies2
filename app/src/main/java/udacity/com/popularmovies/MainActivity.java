package udacity.com.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import udacity.com.popularmovies.model.FavoriteDatabase;
import udacity.com.popularmovies.model.Movie;
import udacity.com.popularmovies.utils.MoviesJsonUtil;
import udacity.com.popularmovies.utils.MovieSearchType;
import udacity.com.popularmovies.utils.TheMovieDBUtils;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler, Observer<List<Movie>> {

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;

    private static final String movieData = "movie_data";

    private FavoriteDatabase mDb;

    private MovieSearchType movieSearchType = MovieSearchType.MOST_POPULAR;

    private int mScrollPosition = RecyclerView.NO_POSITION;
    private List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview_movie);

        mDb = FavoriteDatabase.getsInstance(getApplicationContext());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        loadMoviesData(movieSearchType);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("filter", movieSearchType.name());

        LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if(layoutManager != null && layoutManager instanceof LinearLayoutManager){
            mScrollPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }
        outState.putInt("scroll_position", mScrollPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            movieSearchType = MovieSearchType.valueOf(savedInstanceState.getString("filter"));
            loadMoviesData(movieSearchType);

            mScrollPosition = savedInstanceState.getInt("scroll_position");
            mRecyclerView.smoothScrollToPosition(mScrollPosition);
            mRecyclerView.getLayoutManager().scrollToPosition(mScrollPosition);
        }
    }

    private void loadMoviesData(MovieSearchType action) {
        new FetchMovieSearchTask(this).execute(action.name());
    }

    @Override
    public void onClick(Movie movieSelected) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(movieData, movieSelected);
        startActivity(intentToStartDetailActivity);
    }

    @Override
    public void onChanged(@Nullable List<Movie> movies) {
        if (movieSearchType.equals(MovieSearchType.FAVORITES)) {
            this.movies = movies;
            updateMovies();
        }
    }

    public class FetchMovieSearchTask extends AsyncTask<String, Void, Void> {
        public MainActivity activity;

        public FetchMovieSearchTask(MainActivity a)
        {
            this.activity = a;
        }
        @Override
        protected Void doInBackground(String... params) {
            try {
                if(params.length > 0) {
                   MovieSearchType searchType = MovieSearchType.valueOf(params[0]);
                   if (searchType.equals(MovieSearchType.FAVORITES)) {
                       LiveData<List<Movie>> movies = mDb.movieDAO().loadFavoriteMovies();
                       movies.observe(activity, activity);
                   } else {
                       String jsonMovieResponse = TheMovieDBUtils.searchMovies(searchType);
                       activity.movies = MoviesJsonUtil.convertJsonToMoviesList(jsonMovieResponse);


                   }
                } else {
                    throw new RuntimeException("Invalid search");
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(Void unused) {
            if (!movieSearchType.equals(MovieSearchType.FAVORITES)) {
                updateMovies();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_most_popular) {
            mMovieAdapter.setMovieData(null);
            movieSearchType = MovieSearchType.MOST_POPULAR;
        }
        if (id == R.id.action_now_playing) {
            mMovieAdapter.setMovieData(null);
            movieSearchType = MovieSearchType.NOW_PLAYING;
        }
        if (id == R.id.action_top_rated) {
            mMovieAdapter.setMovieData(null);
            movieSearchType = MovieSearchType.TOP_RATED;
        }
        if (id == R.id.action_favorites) {
            mMovieAdapter.setMovieData(null);
            movieSearchType = MovieSearchType.FAVORITES;
        }
        mScrollPosition = 0;
        loadMoviesData(movieSearchType);
        return super.onOptionsItemSelected(item);
    }

    public void updateMovies() {
        if (movies != null) {
            mMovieAdapter.setMovieData(movies);
        }
        if (mRecyclerView != null && mScrollPosition != RecyclerView.NO_POSITION) {
            mRecyclerView.smoothScrollToPosition(mScrollPosition);
            mRecyclerView.getLayoutManager().scrollToPosition(mScrollPosition);
        }
    }
}
