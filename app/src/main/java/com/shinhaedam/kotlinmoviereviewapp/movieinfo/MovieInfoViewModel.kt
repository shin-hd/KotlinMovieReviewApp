package com.shinhaedam.kotlinmoviereviewapp.movieinfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shinhaedam.kotlinmoviereviewapp.database.MovieAndReviews
import com.shinhaedam.kotlinmoviereviewapp.database.MovieDao
import kotlinx.coroutines.launch

class MovieInfoViewModel(
    val movieKey: Long = 0L,
    dataSource: MovieDao,
    application:Application): AndroidViewModel(application) {
    private val database = dataSource

    private val _movie: LiveData<MovieAndReviews?> = database.getByMovieId(movieKey)
    val movie: LiveData<MovieAndReviews?>
        get() = _movie

    /**
     * 리뷰 작성 프래그먼트로의 이동 플래그
     */
    private val _navigateToAddReview = MutableLiveData<Boolean?>()
    val navigateToAddReview: LiveData<Boolean?>
        get() = _navigateToAddReview

    /**
     * 영화 목록 프래그먼트로의 이동 플래그
     */
    private val _navigateToMovieList = MutableLiveData<Boolean?>()
    val navigateToMovieList: LiveData<Boolean?>
        get() = _navigateToMovieList

    /**
     * 프래그먼트 이동 후 호출
     */
    fun doneNavigating() {
        _navigateToAddReview.value = null
        _navigateToMovieList.value = null
    }

    /**
     * 평가하기 버튼 클릭 시
     */
    fun onEvaluate() {
        viewModelScope.launch {
            _navigateToAddReview.value = true
        }
    }

    /**
     * 목록보기 버튼 클릭 시
     */
    fun onBackToList() {
        viewModelScope.launch {
            _navigateToMovieList.value = true
        }
    }


}