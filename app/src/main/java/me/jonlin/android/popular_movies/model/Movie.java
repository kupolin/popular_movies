package me.jonlin.android.popular_movies.model;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    /*
    original title
    movie poster image thumbnail
    A plot synopsis (called overview in the api)
    user rating (called vote_average in the api)
    release date
     */
    private String originalTitle;

    private String posterThumbnail;

    private String synopsis;

    private String userRating;

    private String releaseDate;


    /**
     * No args constructor for use in serialization
     */
    public Movie() {}

    public void setOriginalTitle(final String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public Movie(String originalTitle, String posterThumbnail, String sypnosis, String userRating, String releaseDate)
    {
        this.originalTitle = originalTitle;
        this.posterThumbnail = posterThumbnail;
        this.synopsis = sypnosis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
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
