package me.jonlin.android.popular_movies.model;

public class Movie {

    private String originalTitle;

    private String posterThumbnail;

    private String synopsis;

    private String userRating;

    private String releaseDate;

    public Movie() {}


    public Movie(String originalTitle, String posterThumbnail, String sypnosis, String userRating,
        String releaseDate) {
        this.originalTitle = originalTitle;
        this.posterThumbnail = posterThumbnail;
        this.synopsis = sypnosis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
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
