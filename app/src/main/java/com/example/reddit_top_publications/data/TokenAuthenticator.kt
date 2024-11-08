package com.example.reddit_top_publications.data

import com.example.reddit_top_publications.network.ApiService
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TokenAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val newToken = runBlocking { refreshToken() }

        return response.request().newBuilder()
            .header("Authorization", "Bearer $newToken")
            .build()
    }

    suspend fun refreshToken(): String {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://oauth.reddit.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val tokenResponse: TokenResponse = apiService.getAccessToken()
        return tokenResponse.access_token
    }
}