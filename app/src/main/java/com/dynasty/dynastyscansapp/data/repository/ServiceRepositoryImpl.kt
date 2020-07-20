package com.dynasty.dynastyscansapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dynasty.dynastyscansapp.data.Resource
import com.dynasty.dynastyscansapp.data.api.ServiceApi
import com.dynasty.dynastyscansapp.data.model.ChapterListModel
import com.dynasty.dynastyscansapp.data.entity.Chapter
import com.dynasty.dynastyscansapp.data.local.ChapterDao
import kotlinx.coroutines.*
import java.lang.Exception

class ServiceRepositoryImpl(
    private val api: ServiceApi,
    private val dao: ChapterDao
) : ServiceRepository {

    override suspend fun getChapterList(page: Int): ChapterListModel {
        return withContext(Dispatchers.IO) {
            api.getChapterList(page.toString())
        }
    }

    override fun getChapterList(): LiveData<Resource<ChapterListModel>> = liveData {
        emit(Resource.loading())
        try {
            val response = withContext(Dispatchers.IO) {
                api.getChapterList()
            }
            emit(Resource.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.error(e.message))
        }
    }

    override fun getChapter(link: String): LiveData<Resource<Chapter>> = liveData {
        emit(Resource.loading())
        try {
            val chapter = withContext(Dispatchers.IO) {
                dao.findByLink(link)
            }
            if (chapter != null) {
                emit(Resource.success(chapter))
            } else {
                val response = withContext(Dispatchers.IO) {
                    api.getChapter(link)
                }
                dao.insertChapter(response)
                emit(Resource.success(response))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.error(e.message))
        }
    }


}