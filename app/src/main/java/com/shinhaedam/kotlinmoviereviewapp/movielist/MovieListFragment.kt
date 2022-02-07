package com.shinhaedam.kotlinmoviereviewapp.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.shinhaedam.kotlinmoviereviewapp.R
import com.shinhaedam.kotlinmoviereviewapp.database.MovieDatabase
import com.shinhaedam.kotlinmoviereviewapp.databinding.FragmentMovieListBinding

class MovieListFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // 프래그먼트 바인딩
        val binding: FragmentMovieListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_movie_list, container, false)

        val application = requireNotNull(this.activity).application

        // 뷰모델팩토리 인스턴스 생성
        val dataSource = MovieDatabase.getInstance(application).movieDatabaseDao
        val viewModelFactory = MovieListViewModelFactory(dataSource, application)

        // 뷰모델 레퍼런스
        val movieListViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(MovieListViewModel::class.java)
        
        // 어댑터
        val adapter = MovieListAdapter()
        binding.movieList.adapter = adapter

        /**
         * 네비게이트 플래그 옵저빙
         * 영화관 찾기 버튼 클릭
         */
        movieListViewModel.navigateToMaps.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    MovieListFragmentDirections
                        .actionMovieListFragmentToMapsFragment())
                movieListViewModel.doneNavigating()
            }
        })

        /**
         * 영화 목록 옵저빙
         * 어댑터 데이터로 매핑
         */
        movieListViewModel.movieList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        return binding.root
    }

}