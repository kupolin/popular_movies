package me.jonlin.android.popular_movies;

import java.util.ArrayList;
import java.util.List;
import me.jonlin.android.popular_movies.model.Movie;

public class MoviesSingleton {
    private static List<Movie> mMovies = null;
    public static List<Movie> getInstance()
    {
        if (mMovies == null)
            mMovies = new ArrayList<>();
        return mMovies;
    }
    private MoviesSingleton(){}

}
