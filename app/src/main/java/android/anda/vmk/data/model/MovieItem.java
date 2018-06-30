package android.anda.vmk.data.model;

public class MovieItem {
    private String Title;
    private String Year;
    private String imdbID;
    private String Poster;

    public String getTitle() {
        return Title;
    }

    public String getYear() {
        return Year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getPosterUrl() {
        return Poster;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
