package udacity.com.popularmovies.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import udacity.com.popularmovies.model.Movie;
import udacity.com.popularmovies.model.MovieReview;
import udacity.com.popularmovies.model.MovieVideo;

public final class MoviesJsonUtil {
    private static final String TAG = MoviesJsonUtil.class.getSimpleName();
    private static final String IMAGE_BASE_PATH = "http://image.tmdb.org/t/p/";
    private static final String POSTER_BASE_PATH = IMAGE_BASE_PATH + "w342";
    private static final String BACKDROP_BASE_PATH = IMAGE_BASE_PATH + "w500";
    private static final String JSON_RESULTS_FIELD = "results";
    private static final String JSON_ID_FIELD = "id";
    private static final String JSON_TITLE_FIELD = "title";
    private static final String JSON_OVERVIEW_FIELD = "overview";
    private static final String JSON_POSTER_FIELD = "poster_path";
    private static final String JSON_VOTE_AVG_FIELD = "vote_average";
    private static final String JSON_RELEASE_DATE_FIELD = "release_date";
    private static final String JSON_BACKDROP_FIELD = "backdrop_path";
    private static final String YOUTUBE_SITE = "YouTube";

    private static final String JSON_AUTHOR = "author";
    private static final String JSON_CONTENT = "content";

    private static final String JSON_NAME = "name";
    private static final String JSON_SITE = "site";
    private static final String JSON_KEY = "key";
    private static final String YOUTUBE_THUMBNAIL_REGEX = "https://img.youtube.com/vi/%s/0.jpg";

    private MoviesJsonUtil(){}

    public static Movie[] convertJsonToMoviesList(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray results = (JSONArray)jsonObject.get(JSON_RESULTS_FIELD);
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

    public static MovieReview[] convertJsonToMovieReviewsList(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray results = (JSONArray)jsonObject.get(JSON_RESULTS_FIELD);
            MovieReview[] movieReviews = new MovieReview[results.length()];
            for (int i = 0; i < results.length(); i++) {
                movieReviews[i] = parseJsonMovieReview((JSONObject)results.get(i));
            }
            return movieReviews;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static MovieVideo[] convertJsonToMovieVideosList(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray results = (JSONArray)jsonObject.get(JSON_RESULTS_FIELD);
            MovieVideo[] movieVideos = new MovieVideo[results.length()];
            for (int i = 0; i < results.length(); i++) {
                movieVideos[i] = parseJsonMovieVideo((JSONObject)results.get(i));
            }
            return movieVideos;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Movie parseJsonMovie(JSONObject jsonMovie) throws JSONException {
        Movie movie = new Movie();
        movie.setMovieId(String.valueOf(jsonMovie.get(JSON_ID_FIELD)));
        movie.setName((String)jsonMovie.get(JSON_TITLE_FIELD));
        movie.setDescription((String)jsonMovie.get(JSON_OVERVIEW_FIELD));
        movie.setImagePath(POSTER_BASE_PATH + jsonMovie.get(JSON_POSTER_FIELD));
        movie.setRating(((Number)jsonMovie.get(JSON_VOTE_AVG_FIELD)).intValue());
        movie.setReleaseDate((String)jsonMovie.get(JSON_RELEASE_DATE_FIELD));

        if(!jsonMovie.isNull(JSON_BACKDROP_FIELD)) {
            movie.setBackdropPath(BACKDROP_BASE_PATH + jsonMovie.get(JSON_BACKDROP_FIELD));
        }
        return movie;
    }

    private static MovieReview parseJsonMovieReview(JSONObject jsonMovieReview) throws JSONException {
        MovieReview movieReview = new MovieReview();
        movieReview.setAuthor((String)jsonMovieReview.get(JSON_AUTHOR));
        movieReview.setContent((String)jsonMovieReview.get(JSON_CONTENT));
        return movieReview;
    }

    private static MovieVideo parseJsonMovieVideo(JSONObject jsonMovieVideos) throws JSONException {
        MovieVideo movieVideo = new MovieVideo();
        String key = (String)jsonMovieVideos.get(JSON_KEY);
        movieVideo.setKey(key);
        movieVideo.setName((String)jsonMovieVideos.get(JSON_NAME));
        String site = (String)jsonMovieVideos.get(JSON_SITE);
        movieVideo.setSite(site);
        if(site.toLowerCase().equals(YOUTUBE_SITE.toLowerCase())) {
            movieVideo.setThumbnailImage(getYoutubeThumbnail(key));
        }
        return movieVideo;
    }

    private static String getYoutubeThumbnail(String key) {
        return String.format(YOUTUBE_THUMBNAIL_REGEX, key);
    }
}
