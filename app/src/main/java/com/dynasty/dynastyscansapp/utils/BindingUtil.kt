package com.dynasty.dynastyscansapp.utils

import android.content.Intent
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.dynasty.dynastyscansapp.data.Resource
import com.dynasty.dynastyscansapp.data.model.TagModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.view_list_chapter.view.*

object BindingUtil {

    @JvmStatic
    @BindingAdapter("isVisibility")
    fun isVisibility(view: View, isVisibility: Boolean) {
        view.visibility = if (isVisibility) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("loadingResource")
    fun <T> loadingResource(view: ProgressBar, resource: Resource<T>?) {
        when (resource?.status) {
            Resource.STATUS_LOADING ->
                view.visibility = View.VISIBLE
            Resource.STATUS_SUCCESS ->
                view.visibility = View.GONE
            Resource.STATUS_ERROR -> {
                view.visibility = View.GONE
                Toast.makeText(view.context, resource.error, Toast.LENGTH_LONG).show()
            }
        }
    }

    @JvmStatic
    @BindingAdapter("addTags")
    fun addTagsView(group: ChipGroup, tags: List<TagModel>) {
        tags.forEach { tag ->
            val chip = Chip(group.context)
            chip.text = tag.name
            chip.setOnClickListener {
                Toast.makeText(group.context, "${tag.name}", Toast.LENGTH_SHORT).show()
//                group.context.startActivity(Intent(group.context,))
            }

            group.addView(chip)
        }
    }

}