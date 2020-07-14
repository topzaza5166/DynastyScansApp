package com.dynasty.dynastyscansapp.data.model

import android.os.Parcelable
import com.dynasty.dynastyscansapp.data.TagType
import com.google.gson.annotations.SerializedName

import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChapterModel(
    @SerializedName("title")
    val title: String?,
    @SerializedName("long_title")
    val long_title: String?,
    @SerializedName("permalink")
    val permalink: String?,
    @SerializedName("tags")
    val tags: List<TagModel>?,
    @SerializedName("pages")
    val pages: List<PageModel>?,
    @SerializedName("released_on")
    val releasedOn: String?,
    @SerializedName("added_on")
    val addedOn: String?
) : Parcelable {

    fun getTag(type: TagType): TagModel? = tags?.find { it.type == type.name }
}