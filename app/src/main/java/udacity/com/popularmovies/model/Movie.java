package udacity.com.popularmovies.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity
public class Movie implements Parcelable {
    @PrimaryKey
    @NonNull
    private String movieId;
    private String name;
    private int rating;
    private String description;
    private String imagePath;
    private String backdropPath;

    public Movie(String movieId, String name, int rating, String description, String imagePath, String backdropPath) {
        this.movieId = movieId;
        this.name = name;
        this.rating = rating;
        this.description = description;
        this.imagePath = imagePath;
        this.backdropPath = backdropPath;
    }

    @Ignore
    public Movie(Parcel parcel) {
        this.movieId = parcel.readString();
        this.name = parcel.readString();
        this.rating = parcel.readInt();
        this.description = parcel.readString();
        this.imagePath = parcel.readString();
        this.backdropPath = parcel.readString();
    }

    @Ignore
    public Movie() {
        super();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.getMovieId());
        parcel.writeString(this.getName());
        parcel.writeInt(this.getRating());
        parcel.writeString(this.getDescription());
        parcel.writeString(this.getImagePath());
        parcel.writeString(this.backdropPath);
    }
}
