package me.jonlin.android.popular_movies;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.squareup.picasso.Picasso;
import me.jonlin.android.popular_movies.database.AppDatabase;
import me.jonlin.android.popular_movies.model.Movie;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";

    private static final int DEFAULT_POSITION = -1;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDb = AppDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        final int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);


        ((Button)findViewById(R.id.favoritebt)).setOnClickListener(new OnClickListener() {
            boolean clicked = false;
            @Override
            public void onClick(View v) {


                clicked = !clicked;
                if (clicked) {
                    v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.lightGreen));

                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDb.movieDao().insert(MoviesSingleton.getInstance().get(position));
                        }
                    });
//                    MainViewModel.getInstance(getApplication()).getValue().add(MoviesSingleton.getInstance().get(position));
                } else {
                    v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.lightGrey));
//                    MainViewModel.getInstance(getApplication()).getValue().remove(MoviesSingleton.getInstance().get(position));
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDb.movieDao().delete(MoviesSingleton.getInstance().get(position));
                        }
                    });
                }
            }
        });

        //get item position in sandwich list

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
