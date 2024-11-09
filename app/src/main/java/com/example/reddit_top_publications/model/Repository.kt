package com.example.reddit_top_publications.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.reddit_top_publications.data.Child
import com.example.reddit_top_publications.network.ApiService
import kotlinx.coroutines.flow.Flow

class Repository(private val apiService: ApiService) {
    fun getTopPublications(): Flow<PagingData<Child>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { TopPublicationsPagingSource(apiService) }
        ).flow
    }
}