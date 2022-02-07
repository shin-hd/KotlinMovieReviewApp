package com.shinhaedam.kotlinmoviereviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
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