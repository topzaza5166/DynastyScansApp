package com.dynasty.dynastyscansapp.data.model


import com.google.gson.annotations.SerializedName

data class ChapterListModel(
    @SerializedName("chapters")
    val chapters: List<Chapter>?,
    @SerializedName("current_page")
    val currentPage: Int?,
    @SerializedName("total_pages")
    val totalPages: Int?
) {
    data class Chapter(
        @SerializedName("permalink")
        val permalink: String?,
        @SerializedName("series")
        val series: String?,
        @SerializedName("tags")
        val tags: List<TagModel>?,
        @SerializedName("title")
        val title: String?
    )
}