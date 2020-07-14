package com.dynasty.dynastyscansapp.ui.chapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dynasty.dynastyscansapp.R
import com.dynasty.dynastyscansapp.data.TagType
import com.dynasty.dynastyscansapp.data.model.ChapterModel
import com.dynasty.dynastyscansapp.utils.hide
import com.dynasty.dynastyscansapp.utils.show
import kotlinx.android.synthetic.main.activity_chapter.*

class ChapterActivity : AppCompatActivity(R.layout.activity_chapter) {

    companion object {

        const val EXTRA_CHAPTER = "extra_chapter"

        fun getIntent(context: Context, chapter: ChapterModel) =
            Intent(context, ChapterActivity::class.java).apply {
                putExtra(EXTRA_CHAPTER, chapter)
            }
    }

    private lateinit var chapter: ChapterModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        intent?.extras?.getParcelable<ChapterModel>(EXTRA_CHAPTER)?.let {
            chapter = it
        } ?: finish()

        supportActionBar?.apply {
            title = chapter.getTag(TagType.Series)?.name ?: chapter.title

            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .add(R.id.contentContainer, ChapterFragment.getInstance(chapter))
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_chapter, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.detail -> {
                ChapterDetailDialog.newInstance(chapter).show(
                    supportFragmentManager, ChapterDetailDialog::class.java.simpleName
                )
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun toggleToolbar() {
        if (toolbar.visibility == View.VISIBLE)
            toolbar.hide()
        else
            toolbar.show()
    }
}