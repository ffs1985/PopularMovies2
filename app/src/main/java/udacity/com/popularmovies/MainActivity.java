package udacity.com.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import udacity.com.popularmovies.model.Movie;
import udacity.com.popularmovies.utils.MoviesJsonUtil;
import udacity.com.popularmovies.utils.MovieSearchType;
import udacity.com.popularmovies.utils.TheMovieDBUtils;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;
    private static final String movieData = "movie_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movie);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        loadMoviesData(MovieSearchType.MOST_POPULAR);
    }

    private void loadMoviesData(MovieSearchType action) {
        showMovieDataView();
        new FetchMovieSearchTask().execute(action.name());
    }

    @Override
    public void onClick(Movie movieSelected) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(movieData, movieSelected);
        startActivity(intentToStartDetailActivity);
    }

    private void showMovieDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class FetchMovieSearchTask extends AsyncTask<String, Void, Movie[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie[] doInBackground(String... params) {
            try {
                String jsonMovieResponse;
                if(params.length > 0) {
                   MovieSearchType searchType = MovieSearchType.valueOf(params[0]);
                   switch (searchType) {
                       case TOP_RATED:
                           jsonMovieResponse = TheMovieDBUtils
                                   .topRatedList();
                           break;
                       case NOW_PLAYING:
                           jsonMovieResponse = TheMovieDBUtils
                                   .mostPopularList();
                           break;
                       case MOST_POPULAR:
                       default:
                           jsonMovieResponse = TheMovieDBUtils
                                   .mostPopularList();
                           break;
                   }
                } else {
                    jsonMovieResponse = TheMovieDBUtils
                            .mostPopularList();
                }
                Movie[] simpleJsonMovieData = MoviesJsonUtil.convertJsonToList(jsonMovieResponse);

                return simpleJsonMovieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movie[] movieListData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieListData != null) {
                showMovieDataView();
                mMovieAdapter.setMovieData(movieListData);
            } else {
                showErrorMessage();
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
            loadMoviesData(MovieSearchType.MOST_POPULAR);
            return true;
        }
        if (id == R.id.action_now_playing) {
            mMovieAdapter.setMovieData(null);
            loadMoviesData(MovieSearchType.NOW_PLAYING);
            return true;
        }
        if (id == R.id.action_top_rated) {
            mMovieAdapter.setMovieData(null);
            loadMoviesData(MovieSearchType.TOP_RATED);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
