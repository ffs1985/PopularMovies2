package udacity.com.popularmovies.model;

public class Movie {
    private String name;
    private int rating;
    private String description;
    private String imagePath;

    public Movie(String name, int rating, String description, String imagePath) {
        this.name = name;
        this.rating = rating;
        this.description = description;
        this.imagePath = imagePath;
    }

    public Movie() {
        super();
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
}
