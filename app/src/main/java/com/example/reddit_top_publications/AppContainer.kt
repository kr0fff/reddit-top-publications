package com.example.reddit_top_publications

import android.app.Application
import com.example.reddit_top_publications.network.TokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer : Application() {
    companion object {
        private val retrofitBuilder: Retrofit.Builder =
            Retrofit.Builder()
                .baseUrl("https://oauth.reddit.com/")
                .addConverterFactory(GsonConverterFactory.create())
    }

    private var retrofit: Retrofit = retrofitBuilder.build()

    private val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(TokenInterceptor())


    fun <T> createService(serviceClass: Class<T>): T {
        retrofit = retrofitBuilder.client(httpClient.build()).build()
        return retrofit.create(serviceClass)
    }
}
