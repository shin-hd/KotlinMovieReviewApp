package com.shinhaedam.kotlinmoviereviewapp.movieinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shinhaedam.kotlinmoviereviewapp.R
import com.shinhaedam.kotlinmoviereviewapp.database.Review

class MovieInfoAdapter: RecyclerView.Adapter<MovieInfoAdapter.ViewHolder>() {

    var data = listOf<Review>()
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
        val movieGrade: TextView = itemView.findViewById(R.id.tvMovieGrade)
        val movieComment: TextView = itemView.findViewById(R.id.tvMovieComment)
        val movieReviewer: TextView = itemView.findViewById(R.id.tvMovieReviewer)

        fun bind(item: Review) {
            movieGrade.text = item.grade.toString()
            movieComment.text = item.comment
            movieReviewer.text = item.reviewer
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.item_review_list, parent, false)

                return ViewHolder(view)
            }
        }
    }
}