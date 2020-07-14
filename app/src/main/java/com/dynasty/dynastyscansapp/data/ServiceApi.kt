package com.dynasty.dynastyscansapp.data

import com.dynasty.dynastyscansapp.data.model.ChapterListModel
import com.dynasty.dynastyscansapp.data.model.ChapterModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ServiceApi {

    @GET("/chapters.json")
    suspend fun getChapterList(): ChapterListModel

    @GET("/chapters/{title}.json")
    suspend fun getChapter(@Path("title") title: String): ChapterModel

}