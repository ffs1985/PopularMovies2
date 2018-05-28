package udacity.com.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable{
    private String name;
    private int rating;
    private String description;
    private String imagePath;
    private String backdropPath;

    public Movie(String name, int rating, String description, String imagePath, String backdropPath) {
        this.name = name;
        this.rating = rating;
        this.description = description;
        this.imagePath = imagePath;
        this.backdropPath = backdropPath;
    }

    public Movie(Parcel parcel) {
        this.name = parcel.readString();
        this.rating = parcel.readInt();
        this.description = parcel.readString();
        this.imagePath = parcel.readString();
        this.backdropPath = parcel.readString();
    }

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
        parcel.writeString(this.getName());
        parcel.writeInt(this.getRating());
        parcel.writeString(this.getDescription());
        parcel.writeString(this.getImagePath());
        parcel.writeString(this.backdropPath);
    }
}
