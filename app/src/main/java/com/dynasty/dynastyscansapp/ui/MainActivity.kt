package com.dynasty.dynastyscansapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dynasty.dynastyscansapp.R
import com.dynasty.dynastyscansapp.ui.chapterlist.ChapterListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        supportActionBar?.title = "Dynasty Reader"
    }

}