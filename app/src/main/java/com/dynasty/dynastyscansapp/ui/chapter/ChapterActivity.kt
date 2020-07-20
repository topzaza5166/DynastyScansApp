package com.dynasty.dynastyscansapp.ui.chapter

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.dynasty.dynastyscansapp.R
import com.dynasty.dynastyscansapp.data.Resource
import com.dynasty.dynastyscansapp.data.TagType
import com.dynasty.dynastyscansapp.data.entity.Chapter
import com.dynasty.dynastyscansapp.data.model.ChapterModel
import com.dynasty.dynastyscansapp.data.repository.ServiceRepository
import com.dynasty.dynastyscansapp.utils.hide
import com.dynasty.dynastyscansapp.utils.show
import kotlinx.android.synthetic.main.activity_chapter.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChapterActivity : AppCompatActivity(R.layout.activity_chapter) {

    private val viewModel: ChapterViewModel by viewModel()

    private val repository: ServiceRepository by inject()

    private val mDetector: GestureDetectorCompat by lazy {
        GestureDetectorCompat(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                toggleToolbar()
                return true
            }
        })
    }

    companion object {

        const val EXTRA_CHAPTER = "extra_chapter"

        fun getIntent(context: Context, chapter: ChapterModel) =
            Intent(context, ChapterActivity::class.java).apply {
                putExtra(EXTRA_CHAPTER, chapter)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        intent?.extras?.getParcelable<ChapterModel>(EXTRA_CHAPTER)?.permalink?.let {
            repository.getChapter(it).observe(this, Observer { resources ->
                if (resources.isSuccess()) {
                    progressBar.visibility = View.GONE
                    viewModel.chapter.value = resources.data?.apply {
                        supportActionBar?.title = getTag(TagType.Series)?.name ?: title
                    }
                }
            })
        } ?: finish()

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        lifecycleScope.launchWhenStarted {
            launch {
                delay(500)
                toolbar.hide()
            }
        }

        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .add(R.id.contentContainer, ChapterFragment.getInstance())
                .commit()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let {
            val outRect = Rect()
            toolbar.getGlobalVisibleRect(outRect)
            if (!outRect.contains(it.rawX.toInt(), it.rawY.toInt())) {
                mDetector.onTouchEvent(ev)
            }
        }
        return super.dispatchTouchEvent(ev)
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
                ChapterDetailDialog.newInstance().show(
                    supportFragmentManager, ChapterDetailDialog::class.java.simpleName
                )
                return true
            }
            R.id.share -> {
                shareLink()
                return true
            }
            R.id.browser -> {
                val link = viewModel.chapter.value?.getFullLink()
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
                return true
            }
            R.id.mode -> {
                viewModel.switchMode()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun shareLink() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"

            putExtra(Intent.EXTRA_TEXT, viewModel.chapter.value?.getFullLink())
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun toggleToolbar() {
        if (toolbar.visibility == View.VISIBLE)
            toolbar.hide()
        else
            toolbar.show()
    }
}