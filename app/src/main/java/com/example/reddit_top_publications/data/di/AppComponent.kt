package com.example.reddit_top_publications.data.di

import com.example.reddit_top_publications.AppContainer
import com.example.reddit_top_publications.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class, RepositoryModule::class])
interface AppComponent {
    fun inject(appContainer: AppContainer)
    fun inject(activity: MainActivity)
}