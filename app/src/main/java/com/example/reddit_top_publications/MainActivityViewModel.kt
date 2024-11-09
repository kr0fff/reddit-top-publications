package com.example.reddit_top_publications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.reddit_top_publications.data.Child
import com.example.reddit_top_publications.data.ListingData
import com.example.reddit_top_publications.model.Repository
import com.example.reddit_top_publications.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class ResponseData(
    val listing: ListingData,
    val error: String? = null,
    val isLoading: Boolean = true
)

class MainActivityViewModel(private val repository: Repository) : ViewModel() {
    val topPublications: Flow<PagingData<Child>> = repository.getTopPublications().cachedIn(viewModelScope)
}
