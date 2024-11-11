package com.example.reddit_top_publications.data.di

import com.example.reddit_top_publications.MainActivityViewModelFactory
import com.example.reddit_top_publications.model.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {
    @Provides
    @Singleton
    fun provideMainActivityViewModelFactory(repository: Repository): MainActivityViewModelFactory {
        return MainActivityViewModelFactory(repository)
    }
}