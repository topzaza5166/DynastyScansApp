package com.dynasty.dynastyscansapp.ui.chapterlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.dynasty.dynastyscansapp.BuildConfig
import com.dynasty.dynastyscansapp.R
import com.dynasty.dynastyscansapp.data.api.ServiceApi
import com.dynasty.dynastyscansapp.data.model.ChapterDetailModel
import com.dynasty.dynastyscansapp.data.model.ChapterModel
import com.dynasty.dynastyscansapp.databinding.ViewListChapterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ChapterListAdapter(
    override val coroutineContext: CoroutineContext,
    private val api: ServiceApi,
    private val listener: ((chapter: ChapterDetailModel?) -> Unit)? = null
) : PagedListAdapter<ChapterModel, ChapterListAdapter.ViewHolder>(DiffCallback), CoroutineScope {

    private val chapterList: MutableMap<Int, ChapterDetailModel> = mutableMapOf()

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<ChapterModel>() {
            override fun areItemsTheSame(oldItem: ChapterModel, newItem: ChapterModel): Boolean {
                return oldItem.permalink == newItem.permalink
            }

            override fun areContentsTheSame(oldItem: ChapterModel, newItem: ChapterModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ViewListChapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.clearView()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, position) }
    }

    inner class ViewHolder(private val binding: ViewListChapterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChapterModel, position: Int) {
            binding.apply {
                chapter = item

                chapterList[position]?.let {
                    preview.loadImage(it)
                } ?: item.permalink?.getImageUrl(position)

                itemView.setOnClickListener {
                    listener?.invoke(chapterList[position])
                }
            }

            binding.executePendingBindings()
        }

        fun clearView() {
            binding.preview.setImageBitmap(null)
            binding.entryChipGroup.removeAllViews()
        }

        private fun String.getImageUrl(position: Int) {
            launch {
                val chapter = withContext(Dispatchers.IO) {
                    api.getChapter(this@getImageUrl)
                }

                chapterList[position] = chapter
                binding.preview.loadImage(chapter)
            }
        }

        private fun ImageView.loadImage(chapter: ChapterDetailModel) {
            chapter.pages?.get(0)?.url?.let {
                Glide.with(context)
                    .load("${BuildConfig.BASE_URL}$it")
                    .placeholder(CircularProgressDrawable(context).apply {
                        strokeWidth = 5f
                        centerRadius = 30f
                        setColorSchemeColors(resources.getColor(R.color.colorAccent))
                        start()
                    })
                    .centerCrop()
                    .into(this)
            }
        }
    }
}