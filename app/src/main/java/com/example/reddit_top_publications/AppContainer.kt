package com.example.reddit_top_publications

import android.app.Application
import com.example.reddit_top_publications.data.TokenAuthenticator
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer : Application() {
    companion object {
        /*private val gson = GsonBuilder().setLenient().create()*/
        private val retrofitBuilder: Retrofit.Builder =
            Retrofit.Builder()
                .baseUrl("https://oauth.reddit.com/")
                .addConverterFactory(GsonConverterFactory.create())
    }

    private var retrofit: Retrofit = retrofitBuilder.build()

    private val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        .authenticator(TokenAuthenticator())
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })

    fun <T> createService(serviceClass: Class<T>): T {
        /*if (token != null) {
            httpClient.interceptors().clear()
            httpClient.addInterceptor { chain ->
                val original: Request = chain.request()
                val request: Request = original.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }
            retrofitBuilder.client(httpClient.build())
            retrofit = retrofitBuilder.build()
        }
        return retrofit.create(serviceClass)*/
        retrofit = retrofitBuilder.client(httpClient.build()).build()
        return retrofit.create(serviceClass)
    }
}
