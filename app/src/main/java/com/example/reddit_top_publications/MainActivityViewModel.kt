package com.example.reddit_top_publications

import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reddit_top_publications.data.Listing
import com.example.reddit_top_publications.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.IOException


class MainActivityViewModel : ViewModel() {
    val publicUiState = MutableStateFlow(Listing())

    init {
        getTopPublications()
    }

    /*private suspend fun fetchToken(): String? {
        return try {
            val retrofit =  Retrofit.Builder()
                .baseUrl("https://oauth.reddit.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
           *//* val apiService = AppContainer().createService(ApiService::class.java)*//*
            val credentials = "8Kjz4vJC7wkYvsXCkHqpQQ:R18ikmdNHQ0Xks-QE0Gkqdq3mCyTfQ"
            val username = "user-user1233"
            val password = "zxcvbnm123"
            val userAgent = "PostmanRuntime/7.42.0"
            val basicAuth = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
            val contentLength = (credentials.length + username.length + password.length).toLong()
            val host = "oauth.reddit.com"

            val tokenResponse = retrofit.getAccessToken(
                authorization = basicAuth,
                userAgent = userAgent,
                contentLength = contentLength,
                host = host,
                grantType = "password",
                username = username,
                password = password
            )
            Log.d("TOKEN_RESPONSE", "$tokenResponse || $basicAuth" )
            tokenResponse.access_token
        } catch (e: HttpException) {
            Log.e("FETCH_TOKEN_ERROR", "HTTP error: ${e.response()?.errorBody()?.string()}", e)
            null
        } catch (e: IOException) {
            Log.e("FETCH_TOKEN_ERROR", "Network error", e)
            null
        } catch (e: Exception) {
            Log.e("FETCH_TOKEN_ERROR", "Unexpected error", e)
            null
        }
    }*/
    fun getTopPublications() {
        viewModelScope.launch {
            try {
                /*val token = fetchToken()*/
                val response = AppContainer().createService(ApiService::class.java)
                    .getTopPublications()
                publicUiState.update { it.copy(data = response.data) }
            } catch (e: IOException) {
                // Handle IOException
            } catch (e: HttpException) {
                // Handle HttpException
            }
        }
    }
}
