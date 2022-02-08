package com.shinhaedam.kotlinmoviereviewapp.movieinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.shinhaedam.kotlinmoviereviewapp.MainActivity
import com.shinhaedam.kotlinmoviereviewapp.R
import com.shinhaedam.kotlinmoviereviewapp.database.MovieDatabase
import com.shinhaedam.kotlinmoviereviewapp.databinding.FragmentMovieInfoBinding

class MovieInfoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // 메인 액티비티
        val activity: MainActivity? = activity as MainActivity

        // 프래그먼트 바인딩
        val binding: FragmentMovieInfoBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_movie_info, container, false
        )

        val application = requireNotNull(this.activity).application
        val arguments = MovieInfoFragmentArgs.fromBundle(arguments!!)

        // 뷰모델팩토리 인스턴스 생성
        val dataSource = MovieDatabase.getInstance(application).movieDatabaseDao
        val viewModelFactory = MovieInfoViewModelFactory(arguments.movieKey, dataSource, application)

        // 뷰모델 레퍼런스
        val movieInfoViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(MovieInfoViewModel::class.java)

        // 어댑터
        val adapter = MovieInfoAdapter()
        binding.reviewList.adapter = adapter

        binding.movieInfoViewModel = movieInfoViewModel

        /**
         * 영화 정보 옵저빙
         * 데이터 바인딩
         */
        movieInfoViewModel.movie.observe(viewLifecycleOwner, Observer {
            it?.let {
                // 액션바 타이틀 설정
                activity?.setActionBarTitle(it.movie.title)

                // 어댑터 데이터에 리뷰 매핑
                adapter.data = it.reviews

                // 포스터 선택
                when(it.movie.movieId) {
                    1L -> binding.moviePoster.setImageResource(R.drawable.pirates)
                    2L -> binding.moviePoster.setImageResource(R.drawable.km)
                    3L -> binding.moviePoster.setImageResource(R.drawable.sp)
                    4L -> binding.moviePoster.setImageResource(R.drawable.sing2)
                    5L -> binding.moviePoster.setImageResource(R.drawable.carol)
                    6L -> binding.moviePoster.setImageResource(R.drawable.ar)
                    7L -> binding.moviePoster.setImageResource(R.drawable.hog)
                    else -> binding.moviePoster.setImageResource(R.drawable.hog)
                }

                // 텍스트뷰 바인딩
                binding.openingDate.text = it.movie.openTime.toString()
                binding.genre.text = it.movie.genre.toString()
                binding.director.text = it.movie.director.toString()
                binding.actor.text = it.movie.actor.toString()
                binding.showTime.text = it.movie.showTime.toString()

                // 리뷰들로부터 평균점수 계산
                var averageGrade = 0;
                val numOfReviews = it.reviews.size
                for (i in 0 until numOfReviews)
                    averageGrade += it.reviews.get(i).grade.toInt()

                averageGrade /= numOfReviews
                binding.averageGrade.text = averageGrade.toString()
            }
        })

        /**
         * 리뷰 추가 네비게이트 플래그 옵저빙
         * 평가하기 버튼 클릭
         */
        movieInfoViewModel.navigateToAddReview.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true
                this.findNavController().navigate(
                    MovieInfoFragmentDirections
                        .actionMovieInfoFragmentToAddReviewFragment(movieInfoViewModel.movieKey))

                movieInfoViewModel.doneNavigating()
            }
        })

        /**
         * 영화 목록 네비게이트 플래그 옵저빙
         * 목록보기 버튼 클릭
         */
        movieInfoViewModel.navigateToMovieList.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true
                this.findNavController().navigate(
                    MovieInfoFragmentDirections
                        .actionMovieInfoFragmentToMovieListFragment())

                movieInfoViewModel.doneNavigating()
            }
        })

        return binding.root
    }
}