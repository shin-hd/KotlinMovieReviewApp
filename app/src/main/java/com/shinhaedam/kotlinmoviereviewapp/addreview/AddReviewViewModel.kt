package com.shinhaedam.kotlinmoviereviewapp.addreview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shinhaedam.kotlinmoviereviewapp.database.MovieDao
import com.shinhaedam.kotlinmoviereviewapp.database.Review
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class AddReviewViewModel (
    val movieKey: Long = 0L,
    dataSource: MovieDao,
    application: Application
): AndroidViewModel(application) {
    private val database = dataSource

    /**
     * 영화 정보 프래그먼트로 이동 플래그
     */
    private val _navigateToMovieInfo = MutableLiveData<Boolean?>()
    val navigateToMovieInfo: LiveData<Boolean?>
        get() = _navigateToMovieInfo

    /**
     * 리뷰 추가 플래그
     */
    private val _addReview = MutableLiveData<Boolean?>()
    val addReview: LiveData<Boolean?>
        get() = _addReview

    var review: Review? = null

    /**
     * [MovieInfoFragment]로 이동 후 플래그 초기화
     */
    fun doneNavigating() {
        _addReview.value = null
        _navigateToMovieInfo.value = null
    }

    /**
     * 평가하기 버튼 클릭 시
     */
    fun onEvaluate() {
        viewModelScope.launch {
            _addReview.value = true
        }
    }

    /**
     * 리뷰 추가
     */
    fun insertReview() {
        CoroutineScope(Dispatchers.IO).launch {
            if(review != null) database.insert(review!!)
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