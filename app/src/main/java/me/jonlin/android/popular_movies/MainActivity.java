package me.jonlin.android.popular_movies;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import me.jonlin.android.popular_movies.model.Movie;
import me.jonlin.android.popular_movies.utils.JsonUtils;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    final String URL
        = "https://api.themoviedb.org/3/discover/movie?api_key=2d96c730bfd2d4cfd946208865cdce04&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";
    final static String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/original";
//    /w185

    final static public String API_KEY = "2d96c730bfd2d4cfd946208865cdce04";

    // http://image.tmdb.org/t/p/w185/???/2d96c730bfd2d4cfd946208865cdce04.jpg
//    use bottom one:
//    http://image.tmdb.org/t/p/w185/wwemzKWzjKYJFfCeiB57q3r4Bcm.png
//https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher
    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String json = null;






//        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

//TODO force refresh problem
        //TODO CloseOnError error handling. No internet connection.
// avoid creating several instances, should be singleon
        final OkHttpClient client = new OkHttpClient();
        final okhttp3.Request request = new okhttp3.Request.Builder()
            .url(URL)
            .build();

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    //TODO update mAdapter.notifyDataSetChanged();
                    String json = response.body().string();
                  //  Log.d("testt", response.body().string());

                    JsonUtils.parseMovieJson(json);
                    MainActivity.this.runOnUiThread(new Runnable(){
                        @Override
                        public void run()
                        {
                            //MainActivity.this.populateUI();
                            MainActivity.this.adapter.notifyDataSetChanged();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();















        // data to populate the RecyclerView with
        String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
            "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34",
            "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48"};

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvNumbers);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new MyRecyclerViewAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

        void populateUI() {

        List<Movie> moviesList = MoviesSingleton.getInstance();
        for(Movie movie : moviesList) {
            //TODO: actionbar main title
            String posterImageURL = BASE_IMAGE_URL + movie.getPosterThumbnail();
            ImageView iv = findViewById(R.id.image_iv);

            // library to get images from the web.
            // into(ImageView ) sets url to ImageView for you
            Picasso.with(this).load(posterImageURL).into(iv);

            //setTitle(model.getMainName());

            /*
            TextView tv = findViewById(R.id.origin_tv);
            tv.setText(model.getPlaceOfOrigin());

            tv = findViewById(R.id.description_tv);
            tv.setText(model.getDescription());

            ImageView iv = findViewById(R.id.image_iv);
            // library to get images from the web.
            // into(ImageView ) sets url to ImageView for you
            Picasso.with(this).load(model.getImage()).into(iv);

            tv = findViewById(R.id.also_known_tv);
            tv.setText(model.getAlsoKnownAs().toString().replace("[", "").replace("]", ""));

            tv = findViewById(R.id.ingredients_tv);
            tv.setText(model.getIngredients().toString().replace("[", "").replace("]", ""));

             */
        }
    }

    private class Query {
        OkHttpClient client = new OkHttpClient();

        String run(String url) throws IOException {
            Request request = new okhttp3.Request.Builder()
                .url(url)
                .build();

            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
        }

}


    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);
    }
}

