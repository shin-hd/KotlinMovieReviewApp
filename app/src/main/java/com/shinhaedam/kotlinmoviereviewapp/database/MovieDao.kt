package com.shinhaedam.kotlinmoviereviewapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {
    @Insert
    fun insert(movie:Movie)

    @Insert
    fun insert(vararg review:Review)

    @Update
    fun update(movie:Movie)

    @Query("SELECT * FROM movie_table")
    fun list(): LiveData<MutableList<Movie>>

    @Query("SELECT * FROM movie_table WHERE movieId = :id")
    fun get(id:Long):LiveData<Movie?>

    @Insert
    suspend fun addMovieDb(movies : List<Movie>)

    @Insert
    suspend fun addReviewDb(reviews : List<Review>)

    @Transaction
    @Query("SELECT * FROM movie_table, review_table WHERE movieId = :id")
    fun getByMovieId(id: Long): LiveData<MovieAndReviews?>
}

