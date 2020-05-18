package me.jonlin.android.popular_movies;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import me.jonlin.android.popular_movies.model.Movie;
import me.jonlin.android.popular_movies.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {
/*
    title
    movie poster
    release date
    vote average
    sypnosis
 */
    public static final String EXTRA_POSITION = "extra_position";

    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        //get item position in sandwich list
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);

        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }
        //updates UI
        populateUI(MoviesSingleton.getInstance().get(position));
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /*
        updates UI with model's details
     */
    private void populateUI(Movie model) {
        setTitle(model.getOriginalTitle());
/*
    title
    movie poster
    release date
    vote average
    sypnosis
 */
        TextView tv = findViewById(R.id.release_date);
        tv.setText(model.getOriginalTitle());

        ImageView iv = findViewById(R.id.image_iv);
        // library to get images from the web.
        Log.d("testt", model.getPosterThumbnail());
        // into(ImageView ) sets url to ImageView for you
        Picasso.with(this).load(MainActivity.BASE_IMAGE_URL + model.getPosterThumbnail()).into(iv);

        tv = findViewById(R.id.release_date);
        tv.setText(model.getReleaseDate());

        tv = findViewById(R.id.vote_average);
        tv.setText(model.getUserRating());

        tv = findViewById(R.id.sypnosis);
        tv.setText(model.getSynopsis());
    }
}
