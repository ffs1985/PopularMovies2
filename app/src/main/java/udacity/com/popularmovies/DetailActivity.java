package udacity.com.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import udacity.com.popularmovies.model.Movie;

public class DetailActivity extends AppCompatActivity {
    private TextView tvTitle;
    private TextView tvDescription;
    private ImageView ivPoster;
    private RatingBar rvRating;
    private Movie movie;
    private static final String movieData = "movie_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        ivPoster = findViewById(R.id.ivPoster);
        rvRating = findViewById(R.id.ratingBar);
        rvRating.setMax(10);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            movie = (Movie) getIntent().getParcelableExtra(movieData);
            tvTitle.setText(movie.getName());
            tvDescription.setText(movie.getDescription());
            Picasso.get().load(movie.getBackdropPath())
                    .placeholder(R.drawable.placeholder).into(ivPoster);
            rvRating.setRating(movie.getRating());
        }
    }
}
