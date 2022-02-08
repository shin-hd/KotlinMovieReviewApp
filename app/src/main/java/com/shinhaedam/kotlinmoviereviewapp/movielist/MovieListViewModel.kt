package com.shinhaedam.kotlinmoviereviewapp.movielist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shinhaedam.kotlinmoviereviewapp.database.Movie
import com.shinhaedam.kotlinmoviereviewapp.database.MovieDao
import kotlinx.coroutines.launch

class MovieListViewModel(
    dataSource:MovieDao,
    application:Application
):AndroidViewModel(application) {
    private val database = dataSource

    val movieList: LiveData<MutableList<Movie>> = database.list()

    /**
     * 지도 프래그먼트로의 전환 플래그
     */
    private val _navigateToMaps = MutableLiveData<Boolean?>()
    val navigateToMaps: LiveData<Boolean?>
        get() = _navigateToMaps

    /**
     * [MapsFragment]로 이동 후 호출
     */
    fun doneNavigating() {
        _navigateToMaps.value = null
    }

    /**
     * 영화관 찾기 버튼 클릭 시
     */
    fun onFindTheater() {
        viewModelScope.launch {
            _navigateToMaps.value = true
        }
    }

}