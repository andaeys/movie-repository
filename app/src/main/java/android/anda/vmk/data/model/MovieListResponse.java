package android.anda.vmk.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieListResponse {
    @SerializedName("Search")
    private List<MovieItem> movieList;

    private String totalResults;

    @SerializedName("Response")
    private String responseStatus;

    public List<MovieItem> getMovieList() {
        return movieList;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public String getResponseStatus() {
        return responseStatus;
    }
}
