package com.dynasty.dynastyscansapp.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.dynasty.dynastyscansapp.data.Resource
import com.dynasty.dynastyscansapp.data.api.ServiceApi
import com.dynasty.dynastyscansapp.data.model.ChapterListModel
import com.dynasty.dynastyscansapp.data.model.ChapterModel
import com.dynasty.dynastyscansapp.data.repository.ServiceRepository
import com.dynasty.dynastyscansapp.utils.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ChapterDataSource(
    override val coroutineContext: CoroutineContext,
    private val repository: ServiceRepository,
    private val resource: MutableLiveData<Resource<ChapterListModel>>
) : PageKeyedDataSource<Int, ChapterModel>(), CoroutineScope {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ChapterModel>) {
        launch {
            resource.value = Resource.loading()
            try {
                val chapters = withContext(Dispatchers.IO) {
                    repository.getChapterList(1)
                }
                chapters.chapters?.let {
                    Preferences.latestChapter = chapters
                    callback.onResult(it, null, 2)
                }
                resource.value = Resource.success(chapters)
            } catch (e: Exception) {
                e.printStackTrace()
                resource.value = Resource.error(e.message)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ChapterModel>) {
        launch {
            try {
                val chapters = withContext(Dispatchers.IO) {
                    repository.getChapterList(params.key)
                }
                chapters.chapters?.let {
                    callback.onResult(it, params.key + 1)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                resource.value = Resource.error(e.message)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ChapterModel>) {

    }

}