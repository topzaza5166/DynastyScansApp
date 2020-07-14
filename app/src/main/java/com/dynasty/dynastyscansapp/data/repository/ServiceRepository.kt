package com.dynasty.dynastyscansapp.data.repository

import androidx.lifecycle.LiveData
import com.dynasty.dynastyscansapp.data.Resource
import com.dynasty.dynastyscansapp.data.model.ChapterListModel
import com.dynasty.dynastyscansapp.data.model.ChapterModel

interface ServiceRepository {

    fun getChapterList(): LiveData<Resource<ChapterListModel>>

    fun getChapter(title: String): LiveData<Resource<ChapterModel>>
}