package com.dynasty.dynastyscansapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dynasty.dynastyscansapp.data.Resource
import com.dynasty.dynastyscansapp.data.api.ServiceApi
import com.dynasty.dynastyscansapp.data.model.ChapterListModel
import com.dynasty.dynastyscansapp.data.model.ChapterDetailModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class ServiceRepositoryImpl(
    private val api: ServiceApi
) : ServiceRepository {

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

    override fun getChapter(title: String): LiveData<Resource<ChapterDetailModel>> = liveData {
        emit(Resource.loading())
        try {
            val response = withContext(Dispatchers.IO) {
                api.getChapter(title)
            }
            emit(Resource.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.error(e.message))
        }
    }

}