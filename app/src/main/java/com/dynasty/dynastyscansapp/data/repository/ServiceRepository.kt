package com.dynasty.dynastyscansapp.data.repository

import androidx.lifecycle.LiveData
import com.dynasty.dynastyscansapp.data.Resource
import com.dynasty.dynastyscansapp.data.model.ChapterListModel
import com.dynasty.dynastyscansapp.data.entity.Chapter
import kotlinx.coroutines.Deferred

interface ServiceRepository {

    suspend fun getChapterList(page: Int): ChapterListModel

    fun getChapterList(): LiveData<Resource<ChapterListModel>>

    fun getChapter(link: String): LiveData<Resource<Chapter>>
}