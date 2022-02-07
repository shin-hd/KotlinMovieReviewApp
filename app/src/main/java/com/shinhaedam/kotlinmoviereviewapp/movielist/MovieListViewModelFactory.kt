package com.shinhaedam.kotlinmoviereviewapp.movielist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shinhaedam.kotlinmoviereviewapp.database.MovieDao

class MovieListViewModelFactory (
    private val dataSource: MovieDao,
    private val application: Application) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
                return MovieListViewModel(dataSource, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}