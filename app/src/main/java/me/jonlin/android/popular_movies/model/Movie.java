package me.jonlin.android.popular_movies.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Movie")
public class Movie {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String originalTitle;

    private String posterThumbnail;

    private String synopsis;

    private String userRating;

    private String releaseDate;

    public Movie() {
    }


    public Movie(String originalTitle, String posterThumbnail, String sypnosis, String userRating,
        String releaseDate) {
        this.originalTitle = originalTitle;
        this.posterThumbnail = posterThumbnail;
        this.synopsis = sypnosis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOriginalTitle(final String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterThumbnail() {
        return posterThumbnail;
    }

    public void setPosterThumbnail(final String posterThumbnail) {
        this.posterThumbnail = posterThumbnail;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(final String synopsis) {
        this.synopsis = synopsis;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(final String userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(final String releaseDate) {
        this.releaseDate = releaseDate;
    }

}
