package com.example.reddit_top_publications.data

import android.util.Base64
import android.util.Log
import com.example.reddit_top_publications.network.ApiService
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TokenAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request {
        val newToken = runBlocking { refreshToken() }
        Log.d("Token_Authenticator", "New token: $newToken")
        return response.request.newBuilder()
            .header("Authorization", "Bearer $newToken")
            .build()

    }

    suspend fun refreshToken(): String {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://oauth.reddit.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val credentials = "8Kjz4vJC7wkYvsXCkHqpQQ:R18ikmdNHQ0Xks-QE0Gkqdq3mCyTfQ"
        val username = "user-user1233"
        val password = "zxcvbnm123"
        val userAgent = "PostmanRuntime/7.42.0"
        val basicAuth = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
        val contentLength = (credentials.length + username.length + password.length).toLong()
        val host = "oauth.reddit.com"
        val tokenResponse: TokenResponse = apiService.getAccessToken(
            authorization = basicAuth,
            userAgent = userAgent,
            contentLength = contentLength,
            host = host,
            grantType = "password",
            username = username,
            password = password
        )
        Log.d("TOKEN_RESPONSE", "$tokenResponse || $basicAuth" )
        return tokenResponse.access_token
    }
}