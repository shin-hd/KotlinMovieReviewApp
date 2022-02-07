package com.shinhaedam.kotlinmoviereviewapp.movieinfo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shinhaedam.kotlinmoviereviewapp.database.MovieDao

class MovieInfoViewModelFactory (
    private val movieKey: Long,
    private val dataSource: MovieDao,
    private val application: Application) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieInfoViewModel::class.java)) {
                return MovieInfoViewModel(movieKey, dataSource, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }