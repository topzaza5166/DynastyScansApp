package com.dynasty.dynastyscansapp.ui.chapterlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.dynasty.dynastyscansapp.BuildConfig
import com.dynasty.dynastyscansapp.R
import com.dynasty.dynastyscansapp.data.entity.Chapter
import com.dynasty.dynastyscansapp.data.model.ChapterModel
import com.dynasty.dynastyscansapp.data.repository.ServiceRepository
import com.dynasty.dynastyscansapp.databinding.ViewListChapterBinding

class ChapterListAdapter(
    private val fragment: Fragment,
    private val repository: ServiceRepository,
    private val listener: ((chapter: Chapter?) -> Unit)? = null
) : PagedListAdapter<ChapterModel, ChapterListAdapter.ViewHolder>(DiffCallback) {

    private val chapterList: MutableMap<Int, Chapter> = mutableMapOf()

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
            repository.getChapter(this@getImageUrl)
                .observe(fragment.viewLifecycleOwner, Observer { response ->
                    if (response.isSuccess())
                        response.data?.let { chapter ->
                            chapterList[position] = chapter
                            binding.preview.loadImage(chapter)
                        }
                })
        }

        private fun ImageView.loadImage(chapter: Chapter) {
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