package com.shinhaedam.kotlinmoviereviewapp.movielist

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
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieDate: TextView = itemView.findViewById(R.id.tvMovieDate)
        val movieTitle: TextView = itemView.findViewById(R.id.tvMovieTitle)
        val movieGenre: TextView = itemView.findViewById(R.id.tvMovieGenre)

        fun bind(item: Movie) {
            movieDate.text = item.openTime
            movieTitle.text = item.title
            movieGenre.text = item.genre

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