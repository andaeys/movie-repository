package android.anda.vmk.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient{
    companion object {
        private const val BASE_URL = "http://www.omdbapi.com/"
        private const val TIMEOUT : Long = 30
    }

    fun getClient(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build()

        return Retrofit.Builder().client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}