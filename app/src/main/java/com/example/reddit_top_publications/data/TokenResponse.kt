package com.example.reddit_top_publications.data

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int,
    val scope: String
)
