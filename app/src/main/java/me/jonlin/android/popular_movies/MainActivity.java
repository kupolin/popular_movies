package me.jonlin.android.popular_movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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
import java.util.List;
import me.jonlin.android.popular_movies.model.Movie;
import me.jonlin.android.popular_movies.utils.JsonUtils;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    /*
        final String URL
            = "https://api.themoviedb.org/3/discover/movie?api_key=2d96c730bfd2d4cfd946208865cdce04&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";
        final String POPULARITYURL =
            "https://api.themoviedb.org/3/discover/movie?api_key=2d96c730bfd2d4cfd946208865cdce04&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";
        final String HIGHESTRATEDURL = "https://api.themoviedb.org/3/discover/movie?api_key=2d96c730bfd2d4cfd946208865cdce04&language=en-US&sort_by=vote_count.desc&include_adult=false&include_video=false&page=1";
    */
    String API_KEY = "2d96c730bfd2d4cfd946208865cdce04";

    String PRE_QUERY_URL = "https://api.themoviedb.org/3/discover/movie?api_key=";

    String POST_QUERY_URL = "&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";

    final static String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/original";

    /*
    @param String apiKey - api key
    @param sortBy - sort by with most popular or highest rated. Keywords "popular", "rated"
     */
    String queryUrl(String apiKey, String sortBy) {
        String retStr = PRE_QUERY_URL + API_KEY + POST_QUERY_URL;

        switch (sortBy) {
            case "popular":
                break;
            case "rated":
                retStr = retStr.replaceFirst("popularity.desc", "vote_count.desc");
        }
        return retStr;
    }
    //URL String sort_by method. base url + sort_by + &;
//    /w185

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
       // setSupportActionBar(findViewById(R.id.toolbar));
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Movies");
        /*
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId()==R.id.item1)
                {
                    // do something
                }
                else if(item.getItemId()== R.id.filter)
                {
                    // do something
                }
                else{
                    // do something
                }

                return false;
            }
        });*/
        setSupportActionBar(toolbar);
//        toolbar.inflateMenu(R.menu.menu_main);
//        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

//TODO force refresh problem
        //TODO CloseOnError error handling. No internet connection.
// avoid creating several instances, should be singleon
        updateMovieList("popular");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvNumbers);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new MyRecyclerViewAdapter(this);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    void populateUI() {

        List<Movie> moviesList = MoviesSingleton.getInstance();
        for (Movie movie : moviesList) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_popular:
                updateMovieList("popular");
                break;
            case R.id.menu_rated:
                updateMovieList("rated");
                break;

            default:
        }
        return true;
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        startActivity(intent);

        Toast.makeText(this, "test:" + position, Toast.LENGTH_SHORT).show();
    }

    /*
        @param sortBy - sort by with most popular or highest rated. Keywords "popular", "rated"
    */
    void updateMovieList(final String sortBy) {

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MoviesSingleton.getInstance().clear();
                    final OkHttpClient client = new OkHttpClient();
                    final okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(queryUrl(API_KEY, sortBy))
                        .build();

                    Response response = client.newCall(request).execute();
                    //TODO update mAdapter.notifyDataSetChanged();
                    String json = response.body().string();
                    //  Log.d("testt", response.body().string());

                    JsonUtils.parseMovieJson(json);
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
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
    }
}