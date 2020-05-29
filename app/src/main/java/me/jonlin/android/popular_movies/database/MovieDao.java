package me.jonlin.android.popular_movies.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import me.jonlin.android.popular_movies.model.Movie;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM Movie")
    LiveData<List<Movie>> getAllMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie m);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Movie> m);

    @Delete
    void delete(Movie m);

    @Query("DELETE FROM Movie")
    void deleteAll();
}