package me.jonlin.android.popular_movies.utils;


import java.io.IOException;
import java.util.List;
import me.jonlin.android.popular_movies.MoviesSingleton;
import me.jonlin.android.popular_movies.model.Movie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class JsonUtils {

    public static List<Movie> parseMovieJson(String json) {
        final String resultsStr = "results";
        final String originalTitleStr = "originalTitleStr";
        final String posterImageStr = "poster_path";
        final String sypnosisStr = "overview";
        final String userRating = "vote_average";
        final String releaseDate = "release_date";

        try {
            JSONTokener tokener = new JSONTokener(json);
            // nextValue returns an Object of type:

            //a JSONObject, JSONArray, String, Boolean, Integer, Long, Double or JSONObject#NULL.
            JSONObject jsonObject = (JSONObject) tokener.nextValue();

//          (String mainName, List<String> alsoKnownAs  , String placeOfOrigin, String description, String image,
//           List<String> ingredients)
    /*
    original title
    movie poster image thumbnail
    A plot synopsis (called overview in the api)
    user rating (called vote_average in the api)
    release date
     */
            JSONArray resultsJSONArray = jsonObject.getJSONArray(resultsStr);
            //list of movies in resultsJSONArray
            for (int i = 0; i < resultsJSONArray.length(); i++) {
                Movie model = new Movie();
                MoviesSingleton.getInstance().add(model);

                JSONObject movieJSONObj = resultsJSONArray.getJSONObject(i);

                model.setPosterThumbnail(movieJSONObj.getString(posterImageStr));
                model.setOriginalTitle(movieJSONObj.getString(originalTitleStr));
                model.setReleaseDate(movieJSONObj.getString(releaseDate));
                model.setSynopsis(movieJSONObj.getString(sypnosisStr));
                model.setUserRating(movieJSONObj.getString(userRating));

            }
            return MoviesSingleton.getInstance();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}