package com.shinhaedam.kotlinmoviereviewapp.database

import androidx.room.*

@Entity(tableName = "movie_table")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    var movieId: Long = 0L,

    @ColumnInfo(name="title")
    val title: String,

    @ColumnInfo(name="open_time")
    var openTime: String?,

    @ColumnInfo(name="genre")
    var genre: String?,

    @ColumnInfo(name="director")
    var director: String?,

    @ColumnInfo(name="actor")
    var actor: String?,

    @ColumnInfo(name="show_time")
    var showTime: String?,
)

@Entity(
    tableName = "review_table",
    foreignKeys = [ForeignKey(
        entity = Movie::class,
        parentColumns = arrayOf("movieId"),
        childColumns = arrayOf("movie"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Review(
    @PrimaryKey(autoGenerate = true)
    val reviewId: Long = 0L,

    @ColumnInfo(name="grade")
    var grade: Double,

    @ColumnInfo(name="comment")
    var comment: String,

    @ColumnInfo(name="reviewer")
    var reviewer: String,

    @ColumnInfo(index = true)
    val movie: Long
)

data class MovieAndReviews(
    @Embedded
    val movie: Movie,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "movie"
    )
    val reviews: List<Review>
)