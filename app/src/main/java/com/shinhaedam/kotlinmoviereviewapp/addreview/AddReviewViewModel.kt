package com.shinhaedam.kotlinmoviereviewapp.addreview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shinhaedam.kotlinmoviereviewapp.database.MovieDao
import kotlinx.coroutines.launch

class AddReviewViewModel (
    val movieKey: Long = 0L,
    dataSource: MovieDao,
    application: Application
): AndroidViewModel(application) {
    private val database = dataSource

    /**
     * 영화 정보 프래그먼트로의 이동여부
     */
    private val _navigateToMovieInfo = MutableLiveData<Boolean?>()
    val navigateToMovieInfo: LiveData<Boolean?>
        get() = _navigateToMovieInfo

    /**
     * [MovieInfoFragment]로 이동 후 호출
     */
    fun doneNavigating() {
        _navigateToMovieInfo.value = null
    }

    /**
     * 평가하기 버튼 클릭 시
     */
    fun onEvaluate() {
        viewModelScope.launch {
            _navigateToMovieInfo.value = true
        }
    }

    /**
     * 취소 버튼 클릭 시
     */
    fun onCancel() {
        viewModelScope.launch {
            _navigateToMovieInfo.value = true
        }
    }

}