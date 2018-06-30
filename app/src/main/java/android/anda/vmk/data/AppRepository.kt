package android.anda.vmk.data

import android.anda.vmk.data.model.MovieListResponse
import android.anda.vmk.data.api.ApiClient
import android.anda.vmk.data.api.RestService
import android.anda.vmk.data.model.MovieDetail
import retrofit2.Response
import rx.Observable

class AppRepository(private val apiClient: ApiClient){

    companion object {
        const val API_KEY = "27b9500d"
        const val TYPE_MOVIE = "movie"
        const val TYPE_PLOT = "full"
    }

    fun searchItems(title:String, page:Int) : Observable<Response<MovieListResponse>> {
        val restService = apiClient.getClient().create(RestService::class.java)
        return restService.getMovieList(
                API_KEY,
                title,
                page,
                TYPE_MOVIE)
    }

    fun getDetailMovie(id:String) : Observable<Response<MovieDetail>>{
        val restService = apiClient.getClient().create(RestService::class.java)
        return restService.getMovieDetail(
                API_KEY,
                id,
                TYPE_PLOT)
    }
}