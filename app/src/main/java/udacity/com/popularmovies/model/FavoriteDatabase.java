package udacity.com.popularmovies.model;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase{
    private static final String LOG_TAG = FavoriteDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "favoritemovies";
    private static FavoriteDatabase sInstance;

    public static FavoriteDatabase getsInstance(Context context) {
        if(sInstance == null) {
           synchronized (LOCK) {
               Log.d(LOG_TAG, "Creating new databse instance");
               sInstance = Room.databaseBuilder(context.getApplicationContext(),
                       FavoriteDatabase.class, FavoriteDatabase.DATABASE_NAME).build();
           }
        }
        Log.d(LOG_TAG, "Getting the databse instance");
        return sInstance;
    }

    public abstract MovieDAO movieDAO();
}
