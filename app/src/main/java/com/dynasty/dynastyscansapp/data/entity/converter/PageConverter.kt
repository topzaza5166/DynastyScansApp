package com.dynasty.dynastyscansapp.data.entity.converter

import androidx.room.TypeConverter
import com.dynasty.dynastyscansapp.data.model.PageModel
import com.dynasty.dynastyscansapp.data.model.TagModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PageConverter {

    @TypeConverter
    fun toJson(tags: List<PageModel>?): String? {
        return Gson().toJson(tags)
    }

    @TypeConverter
    fun toTags(json: String?): List<PageModel>? {
        return Gson().fromJson(json, object : TypeToken<List<PageModel>>() {}.type)
    }

}