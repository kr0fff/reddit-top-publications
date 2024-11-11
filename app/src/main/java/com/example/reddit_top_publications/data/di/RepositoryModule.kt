package com.example.reddit_top_publications.data.di

import com.example.reddit_top_publications.model.Repository
import com.example.reddit_top_publications.network.ApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService): Repository {
        return Repository(apiService)
    }
}