package udacity.com.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieVideo implements Parcelable{
    private String key;
    private String name;
    private String site;
    private String thumbnailImage;

    public MovieVideo(String key, String name, String site, String thumbnailImage) {
        this.name = name;
        this.site = site;
        this.thumbnailImage = thumbnailImage;
    }

    public MovieVideo(Parcel parcel) {
        this.key = parcel.readString();
        this.name = parcel.readString();
        this.site = parcel.readString();
        this.thumbnailImage = parcel.readString();
    }

    public MovieVideo() {
        super();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
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
        parcel.writeString(this.getKey());
        parcel.writeString(this.getSite());
        parcel.writeString(this.getName());
        parcel.writeString(this.getThumbnailImage());
    }
}
