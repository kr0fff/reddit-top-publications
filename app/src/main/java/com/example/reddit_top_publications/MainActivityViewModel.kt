package com.example.reddit_top_publications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.reddit_top_publications.data.Child
import com.example.reddit_top_publications.model.Repository
import kotlinx.coroutines.flow.Flow


class MainActivityViewModel(private val repository: Repository) : ViewModel() {
    val topPublications: Flow<PagingData<Child>> = repository.getTopPublications().cachedIn(viewModelScope)
}
