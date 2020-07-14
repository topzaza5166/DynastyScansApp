package com.dynasty.dynastyscansapp.ui.chapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.dynasty.dynastyscansapp.BuildConfig
import com.dynasty.dynastyscansapp.R
import com.dynasty.dynastyscansapp.data.model.PageModel
import kotlinx.android.synthetic.main.view_list_page.view.*

class PageAdapter(
    private val pages: List<PageModel>,
    private val listener: () -> Unit
) :
    RecyclerView.Adapter<PageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.view_list_page, parent, false)
    )

    override fun getItemCount(): Int = pages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pages[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(page: PageModel) {
            itemView.imgPage.apply {
                Glide.with(context)
                    .load("${BuildConfig.BASE_URL}${page.url}")
                    .placeholder(CircularProgressDrawable(context).apply {
                        strokeWidth = 5f
                        centerRadius = 30f
                        start()
                    })
                    .into(this)
            }
            itemView.setOnClickListener {
                listener.invoke()
            }
        }
    }
}