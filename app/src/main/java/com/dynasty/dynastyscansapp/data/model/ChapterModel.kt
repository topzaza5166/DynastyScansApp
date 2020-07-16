package com.dynasty.dynastyscansapp.data.model

import com.google.gson.annotations.SerializedName

data class ChapterModel(
    @SerializedName("permalink")
    val permalink: String?,
    @SerializedName("series")
    val series: String?,
    @SerializedName("tags")
    val tags: List<TagModel>?,
    @SerializedName("title")
    val title: String?
)