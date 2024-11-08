package com.example.reddit_top_publications

import android.app.Application
import com.example.reddit_top_publications.data.TokenAuthenticator
import okhttp3.OkHttpClient
import okhttp3.Request
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
        .authenticator(TokenAuthenticator())

    fun <T> createService(serviceClass: Class<T>, token: String?): T {
        if (token != null) {
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
        return retrofit.create(serviceClass)
    }
}
