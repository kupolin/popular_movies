package me.jonlin.android.popular_movies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import java.io.IOException;
import me.jonlin.android.popular_movies.utils.JsonUtils;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    String API_KEY = "YOUR_API_KEY";

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
    MyRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String json = null;

        //set up menu
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Movies");
        setSupportActionBar(toolbar);

        // set up movie list
        updateMovieList("popular");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvNumbers);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new MyRecyclerViewAdapter(this);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
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
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        startActivity(intent);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /*
        @param sortBy - sort by with most popular or highest rated. Keywords "popular", "rated"
    */
    void updateMovieList(final String sortBy) {
        if(!isNetworkConnected()) {
            closeOnError();
        }

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