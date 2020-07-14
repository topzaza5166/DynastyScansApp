package com.dynasty.dynastyscansapp.utils

import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.dynasty.dynastyscansapp.data.Resource

object BindingUtil {

    @JvmStatic
    @BindingAdapter("isVisibility")
    fun isVisibility(view: View, isVisibility: Boolean) {
        view.visibility = if (isVisibility) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("loadingResource")
    fun <T> loadingResource(view: ProgressBar, resource: Resource<T>) {
        when (resource.status) {
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


}