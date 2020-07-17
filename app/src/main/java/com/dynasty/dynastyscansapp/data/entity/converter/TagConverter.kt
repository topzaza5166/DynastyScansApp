package com.dynasty.dynastyscansapp.data.entity.converter

import androidx.room.TypeConverter
import com.dynasty.dynastyscansapp.data.model.TagModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class TagConverter {

    @TypeConverter
    fun toJson(tags: List<TagModel>?): String? {
        return Gson().toJson(tags)
    }

    @TypeConverter
    fun toTags(json: String?): List<TagModel>? {
        return Gson().fromJson(json, object : TypeToken<List<TagModel>>() {}.type)
    }

}