package com.shinhaedam.kotlinmoviereviewapp.addreview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.shinhaedam.kotlinmoviereviewapp.R
import com.shinhaedam.kotlinmoviereviewapp.database.MovieDatabase
import com.shinhaedam.kotlinmoviereviewapp.database.Review
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

        // 평점
        var grade: Double? = null

        // 평점 선택 어댑터
        val gradeList = listOf("평점", "0.0", "0.5", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5", "5.0", "5.5", "6.0", "6.5", "7.0", "7.5", "8.0", "8.5", "9.0", "9.5", "10.0")
        val adapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, gradeList)
        binding.etReviewValue.adapter = adapter

        /**
         * 평점 선택 리스너
         */
        binding.etReviewValue.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            // 선택 x
            override fun onNothingSelected(p0: AdapterView<*>?) {
                // 동작 x
            }

            // 평점 선택 시
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                // "평점" 선택 시 null, 숫자 선택 시 해당 값 grade로
                grade = if(p2 != 0) gradeList[p2].toDouble() else null
            }
        }

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

        /**
         * 화면전환 플래그 옵저빙
         */
        addReviewViewModel.navigateToMovieInfo.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                // 화면전환
                navigate(addReviewViewModel)
            }
        })

        /**
         * 리뷰 추가 플래그 옵저빙
         */
        addReviewViewModel.addReview.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                val comment = binding.etReviewComment.text.toString()
                val reviewer = binding.etReviewer.text.toString()

                if(grade == null)
                    Toast.makeText(requireContext(), "평점을 입력해주세요!!", Toast.LENGTH_LONG).show()
                else if(comment.isEmpty())
                    Toast.makeText(requireContext(), "평가를 입력해주세요!!", Toast.LENGTH_LONG).show()
                else if(reviewer.isEmpty())
                    Toast.makeText(requireContext(), "평가자를 입력해주세요!!", Toast.LENGTH_LONG).show()
                else {
                    // 리뷰 추가
                    addReviewViewModel.review = Review(grade= grade!!, comment=comment, reviewer=reviewer, movie=addReviewViewModel.movieKey)
                    addReviewViewModel.insertReview()
                    // 화면전환
                    navigate(addReviewViewModel)
                }
            }
        })

        return binding.root
    }

    /**
     * 화면 전환 함수
     */
    private fun navigate(addReviewViewModel: AddReviewViewModel) {
        this.findNavController().navigate(
            AddReviewFragmentDirections
                .actionAddReviewFragmentToMovieInfoFragment(addReviewViewModel.movieKey))

        addReviewViewModel.doneNavigating()
    }
}