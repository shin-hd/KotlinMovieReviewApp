package com.shinhaedam.kotlinmoviereviewapp.movielist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.shinhaedam.kotlinmoviereviewapp.R
import com.shinhaedam.kotlinmoviereviewapp.database.Movie

class MovieListAdapter: RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    var data = listOf<Movie>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieDate: TextView = itemView.findViewById(R.id.tvMovieDate)
        val movieTitle: TextView = itemView.findViewById(R.id.tvMovieTitle)
        val movieGenre: TextView = itemView.findViewById(R.id.tvMovieGenre)

        fun bind(item: Movie, position: Int) {
            movieDate.text = item.openTime
            movieTitle.text = item.title
            movieGenre.text = item.genre

            /**
             * 홀수번째 리스트의 백그라운드 색 변경
             */
            if(position % 2 == 1){
                movieDate.setBackgroundColor(Color.parseColor("#e9ecef"))
                movieTitle.setBackgroundColor(Color.parseColor("#e9ecef"))
                movieGenre.setBackgroundColor(Color.parseColor("#e9ecef"))
            }

            /**
             * 영화 리스트 클릭 시
             * 해당 영화의 영화정보 프래그먼트로 이동
             */
            itemView.setOnClickListener {
                it.findNavController().navigate(
                    MovieListFragmentDirections
                        .actionMovieListFragmentToMovieInfoFragment(item.movieId))
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.item_movie_list, parent, false)

                return ViewHolder(view)
            }
        }
    }
}