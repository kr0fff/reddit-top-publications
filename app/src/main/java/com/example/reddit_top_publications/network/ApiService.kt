package com.example.reddit_top_publications.network

import com.example.reddit_top_publications.data.Listing
import retrofit2.http.GET

interface ApiService {
    @GET("r/popular/top/")
    suspend fun getTopPublications(): Listing
}