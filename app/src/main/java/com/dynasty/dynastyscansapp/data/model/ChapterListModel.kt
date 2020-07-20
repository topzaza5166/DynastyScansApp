package com.dynasty.dynastyscansapp.data.model


import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class ChapterListModel(
    @SerializedName("chapters")
    val chapters: List<ChapterModel>?,
    @SerializedName("current_page")
    val currentPage: Int?,
    @SerializedName("total_pages")
    val totalPages: Int?
)