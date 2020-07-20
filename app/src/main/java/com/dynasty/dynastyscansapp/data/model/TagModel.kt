package com.dynasty.dynastyscansapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TagModel(
    @SerializedName("name")
    val name: String?,
    @SerializedName("permalink")
    val permalink: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("cover")
    val cover: String?
) : Parcelable