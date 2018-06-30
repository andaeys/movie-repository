package android.anda.vmk.data.api;

import android.anda.vmk.data.model.MovieDetail;
import android.anda.vmk.data.model.MovieListResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface RestService {

    @GET(".")
    Observable<Response<MovieListResponse>> getMovieList(
            @Query("apikey") String apiKey,
            @Query("s") String title,
            @Query("page") int page,
            @Query("type") String type
    );

    @GET(".")
    Observable<Response<MovieDetail>> getMovieDetail(
            @Query("apikey") String apiKey,
            @Query("i") String id,
            @Query("plot") String plot
    );
}
