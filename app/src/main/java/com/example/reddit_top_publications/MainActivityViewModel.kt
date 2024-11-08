package com.example.reddit_top_publications

import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reddit_top_publications.data.Listing
import com.example.reddit_top_publications.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class MainActivityViewModel : ViewModel() {
    val publicUiState = MutableStateFlow(Listing())

    init {
        getTopPublications()
    }

    private suspend fun fetchToken(): String {
        val apiService = AppContainer().createService(ApiService::class.java, null)
        val credentials = "8Kjz4vJC7wkYvsXCkHqpQQ:R18ikmdNHQ0Xks-QE0Gkqdq3mCyTfQ"
        val username = "user-user1233"
        val password = "password123"
        val basicAuth = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
        val tokenResponse = apiService.getAccessToken(basicAuth, "password", username, password)
        return tokenResponse.access_token
    }

    fun getTopPublications() {
        viewModelScope.launch {
            try {
                val token = fetchToken()
                val response = AppContainer().createService(ApiService::class.java, "Bearer $token")
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
