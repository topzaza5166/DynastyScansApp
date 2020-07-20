package com.dynasty.dynastyscansapp.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChapterModel(
    @SerializedName("header")
    val header: String?,
    @SerializedName("permalink")
    val permalink: String?,
    @SerializedName("series")
    val series: String?,
    @SerializedName("tags")
    val tags: List<TagModel>?,
    @SerializedName("title")
    val title: String?
) : Parcelable