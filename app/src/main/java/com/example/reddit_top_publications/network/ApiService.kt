package com.example.reddit_top_publications.network

import com.example.reddit_top_publications.data.Listing
import com.example.reddit_top_publications.data.TokenResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @GET("r/popular/top/")
    suspend fun getTopPublications(): Listing
    @FormUrlEncoded
    @POST("/api/v1/access_token")
    suspend fun getAccessToken(
        @Header("Authorization") authorization: String,
        @Header("User-Agent") userAgent: String,
        @Header("Content-Length") contentLength: Long,
        @Header("Host") host: String,
        @Field("grant_type") grantType: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): TokenResponse
}