package com.example.reddit_top_publications.data

import android.util.Base64
import android.util.Log
import com.example.reddit_top_publications.network.ApiService
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class TokenInterceptor : Interceptor {
    companion object {
        private const val CREDENTIALS = "8Kjz4vJC7wkYvsXCkHqpQQ:R18ikmdNHQ0Xks-QE0Gkqdq3mCyTfQ"
        private const val USERNAME = "user-user1233"
        private const val PASSWORD = "zxcvbnm123"
        private const val USER_AGENT = "PostmanRuntime/7.42.0"
        private const val CONTENT_LENGTH = (CREDENTIALS.length + USERNAME.length + PASSWORD.length).toLong()
        private const val HOST = "oauth.reddit.com"
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newToken = runBlocking { refreshToken() }
        Log.d("Token_Interceptor", "New token: $newToken")

        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $newToken")
            .build()

        return chain.proceed(newRequest)
    }


    suspend fun refreshToken(): String {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://oauth.reddit.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val basicAuth = "Basic " + Base64.encodeToString(CREDENTIALS.toByteArray(), Base64.NO_WRAP)
        val tokenResponse: TokenResponse = apiService.getAccessToken(
            authorization = basicAuth,
            userAgent = USER_AGENT,
            contentLength = CONTENT_LENGTH,
            host = HOST,
            grantType = "password",
            username = USERNAME,
            password = PASSWORD
        )
        Log.d("TOKEN_RESPONSE", "$tokenResponse || $basicAuth" )
        return tokenResponse.access_token
    }
}