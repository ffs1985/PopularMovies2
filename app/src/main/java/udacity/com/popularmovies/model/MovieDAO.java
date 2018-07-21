package udacity.com.popularmovies.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MovieDAO {
    @Query("SELECT * FROM Movie")
    LiveData<List<Movie>> loadFavoriteMovies();

    @Insert
    void insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("SELECT * FROM Movie WHERE movieId = :movieId")
    LiveData<Movie> load(String movieId);
}
