package me.jonlin.android.popular_movies.model;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import me.jonlin.android.popular_movies.database.AppDatabase;

public class MainViewModel extends AndroidViewModel {
        private LiveData<List<Movie>> mMovieFavorites;
        public MainViewModel(Application application) {
            super(application);
            AppDatabase database = AppDatabase.getInstance(this.getApplication());
            mMovieFavorites = database.movieDao().getAllMovies();
        }
    public LiveData<List<Movie>> getMovieFavorites() {
        return mMovieFavorites;
    }

        /*
        public LiveData<List<Movie>> getMovies() {
            if (mMovieFavorites == null) {
                mMovieFavorites = new MutableLiveData<List<Movie>>();
            }
            return mMovieFavorites;
        }

        private void loadUsers() {
            // Do an asynchronous operation to fetch users.
        }

         */
    }
