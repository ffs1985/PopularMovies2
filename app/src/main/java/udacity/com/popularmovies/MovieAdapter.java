package udacity.com.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import udacity.com.popularmovies.model.Movie;

public class MovieAdapter extends ArrayAdapter<Movie>{
    private Context activityContext;
    public MovieAdapter(Activity context, List<Movie> moviesList) {
        super(context, 0, moviesList);
        activityContext = context.getApplicationContext();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.movie_image);
        Picasso.get().load(movie.getImagePath())
                .placeholder(R.drawable.placeholder).into(imageView);

        TextView movieNameView = (TextView) convertView.findViewById(R.id.movie_name);
        movieNameView.setText(movie.getName());

        return convertView;
    }
}
