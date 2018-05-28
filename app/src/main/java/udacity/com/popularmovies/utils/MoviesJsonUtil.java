package udacity.com.popularmovies.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import udacity.com.popularmovies.model.Movie;

public class MoviesJsonUtil {
    private static final String TAG = MoviesJsonUtil.class.getSimpleName();
    private static final String IMAGE_BASE_PATH = "http://image.tmdb.org/t/p/";
    private static final String POSTER_BASE_PATH = IMAGE_BASE_PATH + "w342";
    private static final String BACKDROP_BASE_PATH = IMAGE_BASE_PATH + "w500";
    private static final String json_results_field = "results";
    private static final String json_title_field = "title";
    private static final String json_overview_field = "overview";
    private static final String json_poster_field = "poster_path";
    private static final String json_vote_avg_field = "vote_average";
    private static final String json_backdrop_field = "backdrop_path";


    public static Movie[] convertJsonToList(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray results = (JSONArray)jsonObject.get(json_results_field);
            Movie[] movies = new Movie[results.length()];
            for (int i = 0; i < results.length(); i++) {
               movies[i] = parseJsonMovie((JSONObject)results.get(i));
            }
            return movies;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Movie parseJsonMovie(JSONObject jsonMovie) throws JSONException {
        Movie movie = new Movie();
        movie.setName((String)jsonMovie.get(json_title_field));
        movie.setDescription((String)jsonMovie.get(json_overview_field));
        movie.setImagePath(POSTER_BASE_PATH + (String)jsonMovie.get(json_poster_field));
        movie.setRating(((Number)jsonMovie.get(json_vote_avg_field)).intValue());
        movie.setBackdropPath(BACKDROP_BASE_PATH + (String)jsonMovie.get(json_backdrop_field));
        return movie;
    }
}
