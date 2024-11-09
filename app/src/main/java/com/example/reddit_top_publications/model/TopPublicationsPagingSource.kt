package com.example.reddit_top_publications.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.reddit_top_publications.data.Child
import com.example.reddit_top_publications.network.ApiService

class TopPublicationsPagingSource(
    private val apiService: ApiService
): PagingSource<String, Child>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, Child> {
        return try {
            val hashString = params.key ?: ""
            val response = apiService.getTopPublications(after = hashString, limit = params.loadSize)
            LoadResult.Page(
                data = response.data.children,
                prevKey = null,
                nextKey = response.data.after
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Child>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }
}