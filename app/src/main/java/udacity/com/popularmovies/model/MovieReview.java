package udacity.com.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieReview implements Parcelable{
    private String author;
    private String content;

    public MovieReview() {
        super();
    }

    public MovieReview(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public MovieReview(Parcel parcel) {
        this.author = parcel.readString();
        this.content = parcel.readString();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.getAuthor());
        parcel.writeString(this.getContent());
    }
}
