package me.jonlin.android.popular_movies;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.ArrayList;
import java.util.List;
import me.jonlin.android.popular_movies.database.AppDatabase;
import me.jonlin.android.popular_movies.model.Movie;


public class MainViewModel extends AndroidViewModel {
    private static LiveData<List<Movie>> mFavoriteMovies;

    public MainViewModel(Application application) {
        super(application);

        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d("testt", "Actively retrieving the tasks from the DataBase");
        mFavoriteMovies = database.movieDao().getAllMovies();

    }

//    public static LiveData<List<Movie>> getMovies() {
//        return mFavoriteMovies;
//    }

    public static LiveData<List<Movie>> getInstance(Application application)
    {
        if (mFavoriteMovies == null) {
            Log.d("testt", "testing2:");

            AppDatabase database = AppDatabase.getInstance(application);
            Log.d("testt", "Singleton instance:  Actively retrieving the tasks from the DataBase");
            mFavoriteMovies = database.movieDao().getAllMovies();
//            if(mFavoriteMovies == null)
//                new LiveData<Movie>();
//                mFavoriteMovies = new LiveData<List<Movie>>();
        }
        Log.d("testt", "testing:");
        Log.d("testt", mFavoriteMovies.toString());
        if(mFavoriteMovies.getValue() == null)
            Log.d("testt", "null");
else
        Log.d("testt", mFavoriteMovies.getValue().toString());

        return mFavoriteMovies;
    }
}
