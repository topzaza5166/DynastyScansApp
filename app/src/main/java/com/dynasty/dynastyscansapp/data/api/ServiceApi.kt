package com.dynasty.dynastyscansapp.data.api

import com.dynasty.dynastyscansapp.data.model.ChapterListModel
import com.dynasty.dynastyscansapp.data.entity.Chapter
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @GET("/chapters.json")
    suspend fun getChapterList(@Query("page") page: String? = null): ChapterListModel

    @GET("/chapters/{title}.json")
    suspend fun getChapter(@Path("title") title: String): Chapter

}