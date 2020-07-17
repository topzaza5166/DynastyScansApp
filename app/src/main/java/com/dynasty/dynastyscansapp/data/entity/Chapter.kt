package com.dynasty.dynastyscansapp.data.entity

import android.os.Parcelable
import androidx.room.*
import com.dynasty.dynastyscansapp.BuildConfig
import com.dynasty.dynastyscansapp.data.TagType
import com.dynasty.dynastyscansapp.data.entity.converter.PageConverter
import com.dynasty.dynastyscansapp.data.entity.converter.TagConverter
import com.dynasty.dynastyscansapp.data.model.PageModel
import com.dynasty.dynastyscansapp.data.model.TagModel
import com.google.gson.annotations.SerializedName

import kotlinx.android.parcel.Parcelize


@Entity
@Parcelize
data class Chapter(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Int = 0,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String?,

    @ColumnInfo(name = "long_title")
    @SerializedName("long_title")
    val long_title: String?,

    @ColumnInfo(name = "permalink")
    @SerializedName("permalink")
    val permalink: String?,

    @ColumnInfo(name = "tags")
    @field:TypeConverters(TagConverter::class)
    @SerializedName("tags")
    val tags: List<TagModel>?,

    @ColumnInfo(name = "pages")
    @field:TypeConverters(PageConverter::class)
    @SerializedName("pages")
    val pages: List<PageModel>?,

    @ColumnInfo(name = "released_on")
    @SerializedName("released_on")
    val releasedOn: String?,

    @ColumnInfo(name = "added_on")
    @SerializedName("added_on")
    val addedOn: String?
) : Parcelable {

    fun getTag(type: TagType): TagModel? = tags?.find { it.type == type.name }

    fun getFullLink(): String = "${BuildConfig.BASE_URL}/chapters/$permalink"


}