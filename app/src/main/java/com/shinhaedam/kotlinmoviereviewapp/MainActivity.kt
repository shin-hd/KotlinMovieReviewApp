package com.shinhaedam.kotlinmoviereviewapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * 액션바 타이틀 수정
     */
    fun setActionBarTitle(title: String?) {
        supportActionBar?.title = title
    }

    /*
    fun setBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    */
}