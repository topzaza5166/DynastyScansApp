package com.dynasty.dynastyscansapp.ui.chapterlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.dynasty.dynastyscansapp.BuildConfig
import com.dynasty.dynastyscansapp.R
import com.dynasty.dynastyscansapp.data.ServiceApi
import com.dynasty.dynastyscansapp.data.model.ChapterListModel.Chapter
import com.dynasty.dynastyscansapp.data.model.ChapterModel
import kotlinx.android.synthetic.main.view_list_chapter.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ChapterListAdapter(
    override val coroutineContext: CoroutineContext,
    private val api: ServiceApi,
    private val list: List<Chapter>,
    private val listener: ((chapter: ChapterModel?) -> Unit)? = null
) : RecyclerView.Adapter<ChapterListAdapter.ViewHolder>(), CoroutineScope {

    private val chapterList: MutableMap<Int, ChapterModel> = mutableMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.view_list_chapter, parent, false)
    )

    override fun getItemCount(): Int = list.size

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.clearImage()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        fun bind(item: Chapter, position: Int) {
            itemView.apply {
                tvTitleName.text = item.title
                tvSeries.text = item.series

                chapterList[position]?.let {
                    preview.loadImage(it)
                } ?: item.permalink?.getImageUrl(position)

                cardView.setOnClickListener {
                    listener?.invoke(chapterList[position])
                }
            }
        }

        fun clearImage() {
            itemView.preview.setImageBitmap(null)
        }

        private fun String.getImageUrl(position: Int) {
            launch {
                val chapter = withContext(Dispatchers.IO) {
                    api.getChapter(this@getImageUrl)
                }

                chapterList[position] = chapter
                itemView.preview.loadImage(chapter)
            }
        }

        private fun ImageView.loadImage(chapter: ChapterModel) {
            chapter.pages?.get(0)?.url?.let {
                Glide.with(context)
                    .load("${BuildConfig.BASE_URL}$it")
                    .placeholder(CircularProgressDrawable(context).apply {
                        strokeWidth = 5f
                        centerRadius = 30f
                        start()
                    })
                    .centerCrop()
                    .into(this)
            }
        }
    }
}