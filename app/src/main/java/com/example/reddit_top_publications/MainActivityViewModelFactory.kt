package com.example.reddit_top_publications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.reddit_top_publications.model.Repository

class MainActivityViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}