package com.shinhaedam.kotlinmoviereviewapp.addreview

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
import com.shinhaedam.kotlinmoviereviewapp.databinding.FragmentAddReviewBinding
import com.shinhaedam.kotlinmoviereviewapp.movieinfo.MovieInfoFragmentArgs

class AddReviewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // 프래그먼트 바인딩
        val binding: FragmentAddReviewBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_review, container, false
        )

        val application = requireNotNull(this.activity).application
        val arguments = MovieInfoFragmentArgs.fromBundle(arguments!!)

        // 뷰모델팩토리 인스턴스 생성
        val dataSource = MovieDatabase.getInstance(application).movieDatabaseDao
        val viewModelFactory = AddReviewViewModelFactory(arguments.movieKey, dataSource, application)

        // 뷰모델 레퍼런스
        val addReviewViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(AddReviewViewModel::class.java)

        binding.addReviewViewModel = addReviewViewModel

        // 화면전환 옵저빙
        addReviewViewModel.navigateToMovieInfo.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true
                this.findNavController().navigate(
                    AddReviewFragmentDirections
                        .actionAddReviewFragmentToMovieInfoFragment(addReviewViewModel.movieKey))

                addReviewViewModel.doneNavigating()
            }
        })

        return binding.root
    }
}