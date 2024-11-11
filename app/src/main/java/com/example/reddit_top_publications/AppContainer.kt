package com.example.reddit_top_publications

import android.app.Application
import com.example.reddit_top_publications.data.di.AppComponent
import com.example.reddit_top_publications.data.di.DaggerAppComponent
import com.example.reddit_top_publications.network.ApiService
import javax.inject.Inject

class AppContainer : Application() {
    lateinit var appComponent: AppComponent

    @Inject
    lateinit var apiService: ApiService

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().build()
        appComponent.inject(this)
    }
}
